package band.effective.office.tvServer.api.mattermost.model

import kotlinx.serialization.Serializable

@Serializable
data class UpdadeRequest(
    val channel_id: String,
    val file_ids: List<String>,
    val message: String,
    val metadata: MetadataXXX,
    val root_id: String
)