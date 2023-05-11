package band.effective.mattermost

import band.effective.core.Either
import band.effective.core.ErrorReason
import band.effective.mattermost.models.FileInfo
import band.effective.mattermost.models.UserInfo
import band.effective.mattermost.models.response.models.EmojiInfo
import band.effective.mattermost.models.response.models.EmojiInfoForApi
import band.effective.mattermost.models.response.ResponseGetPostsForChannel
import band.effective.mattermost.models.response.models.Channel
import band.effective.mattermost.models.response.models.Post


interface MattermostRepository {
    suspend fun getChannels(): Either<ErrorReason, List<Channel>>

    suspend fun getPostsFromChannel(channelId: String, sinceTime: Long): Either<ErrorReason, ResponseGetPostsForChannel>

    suspend fun downloadFile(fileId: String): ByteArray?

    suspend fun getAllPostsFromChannels(): Either<ErrorReason, List<Post>>

    suspend fun getFilesIdsFromPosts(): Either<ErrorReason, List<FileInfo>>

    suspend fun makeReaction(emojiInfo: EmojiInfo): Either<ErrorReason, EmojiInfoForApi>

    suspend fun getUserIdFromToken(): Either<ErrorReason, UserInfo>

}