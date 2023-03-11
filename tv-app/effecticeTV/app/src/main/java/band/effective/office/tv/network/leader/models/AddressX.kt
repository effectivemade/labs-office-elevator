package band.effective.office.tv.network.leader.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AddressX(
    val apartment: Any?,
    val building: Any?,
    val city: String?,
    @Json(name = "city_id")
    val cityId: Int?,
    val country: String?,
    @Json(name = "country_id")
    val countryId: Int?,
    @Json(name = "geo_point")
    val geoPoint: String?,
    @Json(name = "geo_point_zoom")
    val geoPoint_zoom: String?,
    val house: String?,
    val id: Int?,
    val letter: Any?,
    val place: Any?,
    val postCode: Any?,
    val region: String?,
    @Json(name = "region_id")
    val regionId: Int?,
    val street: String?,
    val timezone: Any?,
    val title: String?,
    @Json(name = "user_id")
    val userId: Any?,
    val wing: Any?
)