package band.effective.office.tv.network.leader.models.EventInfo

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Similar(
    val city: Any?,
    val date_end: String,
    val date_start: String,
    val full_name: String,
    val id: Int,
    val photo: String
)