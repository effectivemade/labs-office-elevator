package band.effective

import band.effective.core.Either
import band.effective.mattermost.MattermostRepository
import band.effective.mattermost.models.response.models.EmojiInfo
import band.effective.synology.SynologyRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async

class BotManager(
    private val mattermost: MattermostRepository,
    private val synology: SynologyRepository,
    private val coroutineScope: CoroutineScope
) {
    suspend fun updatePhoto () {
        when (val filesIds = mattermost.getFilesIdsFromPosts()) {
            is Either.Success -> {
                filesIds.data.forEach { fileInfo ->
                    coroutineScope.async {
                        mattermost.downloadFile(fileInfo.id)?.let { filesByte ->
                            // TODO вынести в функцию
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
                println(filesIds.error.message)
            }
        }
    }
}
