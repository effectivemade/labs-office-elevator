package band.effective.office.tv.repository.impl

import band.effective.office.tv.BuildConfig
import band.effective.office.tv.model.domain.Resource
import band.effective.office.tv.core.network.entity.Either
import band.effective.office.tv.core.network.entity.ErrorReason
import band.effective.office.tv.core.network.entity.unpack
import band.effective.office.tv.network.synology.SynologyApi
import band.effective.office.tv.network.synology.models.response.SynologyAuthResponse
import band.effective.office.tv.network.synology.models.response.SynologyListResponse
import band.effective.office.tv.repository.SynologyPhoto
import java.net.URLEncoder
import javax.inject.Inject

class SynologyPhotoImpl @Inject constructor(
    private val synologyApi: SynologyApi,
) : SynologyPhoto {
    private var dirPath: String = ""
    private var isAuth: Boolean = false
    private var sid: String = ""

    override suspend fun getPhotosUrl(folderPath: String): Resource<List<String>> {
        var authResponse: Resource<Boolean>? = null
        if (!isAuth) authResponse = auth()

        if (isAuth) {
            val res: Either<ErrorReason, SynologyListResponse> =
                synologyApi.getFiles(
                    sid = sid,
                    version = 1,
                    method = "list",
                    folderPath = folderPath
                )

            return res.unpack(
                success = { response ->
                    val paths = response.data.files.map { formThumbPhoto(it.path) }
                    Resource.Data(paths)
                },
                error = { error ->
                    Resource.Error(error.message ?: "")
                }
            )
        }
        val message = (authResponse as Resource.Error).error
        return Resource.Error(message)
    }

    override suspend fun auth(): Resource<Boolean> {
        val res: Either<ErrorReason, SynologyAuthResponse> =
            synologyApi.auth(
                version = 3,
                method = "login",
                login = BuildConfig.synologyLogin,
                password = BuildConfig.synologyPassword,
            )

        return res.unpack(
            success = { response ->
                sid = response.data?.sid ?: ""
                val isSuccess = response.isSuccess
                isAuth = isSuccess
                Resource.Data(isSuccess)
            },
            error = { error ->
                isAuth = false
                Resource.Error(error.message ?: "")
            }

        )
    }

    private fun formThumbPhoto(photoPath: String): String {
        val encodePhotoPath = URLEncoder.encode(photoPath, "utf-8")
        return "${BuildConfig.apiSynologyUrl}/webapi/entry.cgi?api=SYNO.FileStation.Thumb&version=2&_sid=${sid}&method=get&path=${encodePhotoPath}"
    }
}