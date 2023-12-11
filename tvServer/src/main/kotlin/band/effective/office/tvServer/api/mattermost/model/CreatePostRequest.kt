package band.effective.office.tvServer.api.mattermost.model

import kotlinx.serialization.Serializable

@Serializable
data class CreatePostRequest(
    val channel_id: String,
    val file_ids: List<String>? = null,
    val message: String,
    val metadata: MetadataX? = null,
    val root_id: String? = null
)