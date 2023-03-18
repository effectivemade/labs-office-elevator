package band.effective.office.tv.model.domain.synology

import band.effective.office.tv.BuildConfig
import band.effective.office.tv.network.synology.models.response.SynologyListResponse
import java.net.URLEncoder

data class PhotoDomain(
    val photoThumb: String
)

fun SynologyListResponse.toDomain(sid: String) =
    data.files.map {
        PhotoDomain(
            photoThumb = formThumbPhoto(it.path, sid)
        )
    }

fun formThumbPhoto(photoPath: String, sid: String): String {
    val encodePhotoPath = URLEncoder.encode(photoPath, "utf-8")
    return "${BuildConfig.apiSynologyUrl}/webapi/entry.cgi?api=SYNO.FileStation.Thumb&version=2&_sid=${sid}&method=get&path=${encodePhotoPath}"
}
