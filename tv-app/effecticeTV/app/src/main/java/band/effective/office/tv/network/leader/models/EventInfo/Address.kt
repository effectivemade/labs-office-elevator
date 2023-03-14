package band.effective.office.tv.network.leader.models.EventInfo

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Address(
    val apartment: Any?,
    val building: Any?,
    val city: String,
    val city_id: Int,
    val country: String,
    val country_id: Int,
    val geo_point: String,
    val geo_point_zoom: String,
    val house: String,
    val id: Int,
    val letter: Any??,
    val place: Any?,
    val post_code: Any?,
    val region: String,
    val region_id: Int,
    val street: String,
    val timezone: Any?,
    val title: String,
    val user_id: Any?,
    val wing: Any?
)