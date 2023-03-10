package band.effective.office.tv.network.leader.models

import com.squareup.moshi.Json

data class DataX(
    @Json(name = "items")
    val items: List<Item>,
    @Json(name = "meta")
    val meta: MetaX
)