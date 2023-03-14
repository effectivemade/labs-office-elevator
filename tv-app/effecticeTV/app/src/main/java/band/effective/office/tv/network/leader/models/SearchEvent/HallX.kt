package band.effective.office.tv.network.leader.models.SearchEvent

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class HallX(
    val capacity: Int?,
    val id: Int?,
    val name: String?,
    val photos: List<PhotoX>,
    val preparePeriod: Int?,
    val square: String?,
    val tags: List<String>?,
    val type: String?
)