package band.effective.mattermost.models

data class FileInfo(
        val id: String,
        val fileName: String,
        val fileType: String,
        val postId: String
)