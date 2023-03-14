package band.effective.office.tv.network.leader.models.EventInfo

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Hall(
    val capacity: Int,
    val id: Int,
    val name: String,
    val photos: List<Photo>,
    val preparePeriod: Int,
    val square: String,
    val tags: List<String>,
    val type: String
)