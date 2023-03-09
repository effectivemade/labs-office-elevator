package band.effective.office.tv.leader.models

import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("_items")
    val items: List<Event>,
    @SerializedName("_meta")
    val meta: Meta
)
