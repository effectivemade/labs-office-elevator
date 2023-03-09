package band.effective.office.tv.leader.models

data class Address(
    val id: Int,
    val city_id:Int,
    val region_id: Int,
    val country_id: Int,
    val post_code: Int?,
    val street: String,
    val house:String,
    val building:Int?,
    val wing:String?,
    val apartment: String?,
    val place: String?,
    val geo_point: String,
    val geo_point_zoom:Int,
    val letter:String?,
    val user_id:Int?,
    val city:String,
    val region: String,
    val country: String,
    val title: String,
    val timezone: String?
)
