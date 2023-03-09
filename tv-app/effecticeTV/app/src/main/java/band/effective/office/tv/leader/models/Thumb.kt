package band.effective.office.tv.leader.models

import com.google.gson.annotations.SerializedName

data class Thumb(
    @SerializedName("180")
    val p180: String,
    @SerializedName("360")
    val p360: String,
    @SerializedName("520")
    val p520: String

)
