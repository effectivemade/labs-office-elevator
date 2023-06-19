package band.effective.synology.models.respone

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UploadPhotoResponse(
    @Json(name = "data")
    val uploadedPhoto: Data?,
    @Json(name = "success")
    val success: Boolean
)
@JsonClass(generateAdapter = true)
data class Data(
    @Json(name = "action")
    val action: String,
    @Json(name = "id")
    val id: Int,
    @Json(name = "unit_id")
    val unitId: Int
)