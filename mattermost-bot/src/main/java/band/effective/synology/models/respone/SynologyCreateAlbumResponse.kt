package band.effective.synology.models.respone

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SynologyCreateAlbumsResponse(
        @Json(name = "data")
        val albumsData: AlbumsInfoCreate,
        @Json(name = "success")
        val success: Boolean
)

@JsonClass(generateAdapter = true)
data class AlbumsInfoCreate(
        @Json(name = "album")
        val album: Album
)
