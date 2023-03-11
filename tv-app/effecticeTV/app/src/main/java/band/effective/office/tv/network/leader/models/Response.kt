package band.effective.office.tv.network.leader.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Response(
    //@Json(name = "`data`")
    val `data`: DataX
)