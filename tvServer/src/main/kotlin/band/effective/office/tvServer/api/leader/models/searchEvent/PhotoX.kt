package band.effective.office.tvServer.api.leader.models.searchEvent

import kotlinx.serialization.Serializable

@Serializable
data class PhotoX(
    val full: String,
    val thumb: ThumbX
)