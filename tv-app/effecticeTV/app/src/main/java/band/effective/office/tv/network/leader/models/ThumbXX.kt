package band.effective.office.tv.network.leader.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ThumbXX(
    //@Json(name = "180")
    val `180`: String,
   // @Json(name = "360")
    val `360`: String,
   // @Json(name = "520")
    val `520`: String
)