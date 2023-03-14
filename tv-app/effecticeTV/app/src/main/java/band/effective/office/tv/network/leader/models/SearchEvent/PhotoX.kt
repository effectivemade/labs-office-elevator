package band.effective.office.tv.network.leader.models.SearchEvent

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PhotoX(
    val full: String,
    val thumb: ThumbX
)