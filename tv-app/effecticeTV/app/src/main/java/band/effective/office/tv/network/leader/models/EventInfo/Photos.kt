package band.effective.office.tv.network.leader.models.EventInfo

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Photos(
    val full: String
)