package band.effective.synology.models.respone

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AddPhotoToAlbumResponse(
    @Json(name = "data")
    val dataOfAdded: DataOfAdded,
    @Json(name = "success")
    val success: Boolean
)
@JsonClass(generateAdapter = true)
data class DataOfAdded(
    @Json(name = "error_list")
    val errorList: List<Any>
)
