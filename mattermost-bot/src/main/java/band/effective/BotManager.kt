package band.effective

import band.effective.core.Either
import band.effective.mattermost.MattermostRepository
import band.effective.mattermost.models.response.models.EmojiInfo
import band.effective.mattermost.models.response.models.EmojiInfoForApi
import band.effective.synology.SynologyRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import java.util.Calendar

class BotManager(
    private val mattermost: MattermostRepository,
    private val synology: SynologyRepository,
    private val coroutineScope: CoroutineScope
) {
    suspend fun updatePhoto () {
        when (val files = mattermost.getFilesIdsFromPosts()) {
            is Either.Success -> {
                files.data.forEach { fileInfo ->
                    coroutineScope.async {
                        mattermost.downloadFile(fileInfo.id)?.let { filesByte ->
                            when ( val uploadedFile = synology.uploadPhotoToAlbum(
                                    file = filesByte, fileName = fileInfo.fileName, fileType = fileInfo.fileType)
                            ) {
                                is Either.Success-> {
                                    if (uploadedFile.data.success) {
                                        val emojiInfo = EmojiInfo(
                                                createAt = System.currentTimeMillis(),
                                                emojiName = MattermostSettings.emojiToSaveSuccess,
                                                postId = fileInfo.postId,
                                        )
                                        mattermost.makeReaction(emojiInfo)
                                    }
                                }
                                is Either.Failure -> {
                                    println(uploadedFile.error.message)
                                }
                            }
                        }
                    }
                }
            }
            is Either.Failure -> {
                println(files.error.message)
            }
        }
    }
}
