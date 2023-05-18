package band.effective.mattermost

import band.effective.MattermostSettings
import band.effective.core.Either
import band.effective.core.ErrorReason
import band.effective.mattermost.models.FileInfo
import band.effective.mattermost.models.response.ResponseGetPostsForChannel
import band.effective.core.mattermostApi
import band.effective.mattermost.models.UserInfo
import band.effective.mattermost.models.response.models.EmojiInfo
import band.effective.mattermost.models.response.models.EmojiInfoForApi
import band.effective.mattermost.models.response.models.Channel
import band.effective.mattermost.models.response.models.Post
import band.effective.mattermost.models.response.toUserInfo
import kotlinx.coroutines.*
import okhttp3.RequestBody
import org.json.JSONObject
import java.util.Calendar
import java.util.Collections

class MattermostRepositoryImpl(private val token: String, private val coroutineScope: CoroutineScope) : MattermostRepository {

    private var userId: String? = null

    init {
        initUser()
    }

    private fun initUser() {
        coroutineScope.launch {
            when(val userInfo = getUserIdFromToken()) {
                is Either.Success -> {
                    userId = userInfo.data.userId
                }
                is Either.Failure -> {
                    throw Error("Token is not correct or user not found. \n Token should be like this: Bearer <token from mattermost>")
                }
            }
        }
    }
    override suspend fun getChannels(): Either<ErrorReason, List<Channel>> =
            mattermostApi.getChannelMattermost(token = token)

    override suspend fun getPostsFromChannel(channelId: String, sinceTime: Long): Either<ErrorReason, ResponseGetPostsForChannel> =
            mattermostApi.getPostsFromChannel(channelId = channelId, token = token, sinceTime = sinceTime)

    override suspend fun downloadFile(fileId: String): ByteArray? {
        val response = mattermostApi.downloadFile(token = token, fileId = fileId)
        val buffer = response.body()?.byteStream()?.readBytes()
        println("read byte from mattermost ${buffer?.size}")
        response.body()?.byteStream()?.close()
        return buffer
    }

    override suspend fun getAllPostsFromChannels(): Either<ErrorReason, List<Post>> {
        when (val channels = mattermostApi.getChannelMattermost(token)) {
            is Either.Success -> {
                val postsCache = Collections.synchronizedList(mutableListOf<Post>())
                channels.data.forEach { channel ->
                    withContext(Dispatchers.IO) {
                        val posts = mattermostApi.getPostsFromChannel(
                                token = token,
                                channelId = channel.id,
                                sinceTime = System.currentTimeMillis() - MILLISECOND_IN_DAY
                        )
                        when (posts) {
                            is Either.Failure -> {
                                println(posts.error.message)
                            }

                            is Either.Success -> {
                                postsCache.addAll(posts.data.posts.postsData)
                            }
                        }
                    }
                }
                return Either.Success(postsCache.toList())
            }
            is Either.Failure -> {
                return channels
            }
        }
    }

    /*
    This method return fileIds from posts, what has "star" reaction without "save"
    * */
    override suspend fun getFilesIdsFromPosts(): Either<ErrorReason, List<FileInfo>> =
            when (val posts = getAllPostsFromChannels()) {
                is Either.Success -> {
                    val postsWithReaction = posts.data.filter { post ->
                        if (post.metadata.reactions != null) {
                            (post.metadata.reactions.count { reaction -> reaction.emoji_name == MattermostSettings.emojiToRequestSave } > 0 &&
                                    post.metadata.reactions.count { reaction -> reaction.emoji_name == MattermostSettings.emojiToSaveSuccess } == 0)
                        } else false
                    }
                    val filesInPostsWithReaction = postsWithReaction.map { post -> post.metadata.files }
                    val files: MutableList<FileInfo> = mutableListOf()
                    // TODO
                    filesInPostsWithReaction.flatMap { listIsFile ->

                    }
                    filesInPostsWithReaction.forEach { filesInPost ->
                        filesInPost?.forEach { file ->
                            if (file.mime_type.contains("image")) files.add(FileInfo(file.id, file.name, file.mime_type, file.post_id))
                        }
                    }
                    Either.Success(files.toList())
                }

                is Either.Failure -> {
                    posts
                }
            }

    override suspend fun makeReaction(emojiInfo: EmojiInfo): Either<ErrorReason, EmojiInfoForApi> =
            mattermostApi.makeReaction(
                token = token,
                emojiParams = createJsonRequestBody(emojiInfo.mapForApi(userId!!))
            )

    override suspend fun getUserIdFromToken(): Either<ErrorReason, UserInfo> =
            when(val userInfo = mattermostApi.getUserInfoFromToken(token)) {
                is Either.Success -> {
                    Either.Success(userInfo.data.toUserInfo())
                }
                is Either.Failure -> {
                Either.Failure(userInfo.error)
            }
        }

    private fun createJsonRequestBody(params: Map<String, Any>) =
            RequestBody.create(
                    okhttp3.MediaType.parse("application/json; charset=utf-8"),
                    JSONObject(params).toString())
}

private val MILLISECOND_IN_DAY = 86400000