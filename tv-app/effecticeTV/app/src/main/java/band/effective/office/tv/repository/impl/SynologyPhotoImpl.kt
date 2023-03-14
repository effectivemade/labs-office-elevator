package band.effective.office.tv.repository.impl

import band.effective.office.tv.BuildConfig
import band.effective.office.tv.core.network.BaseError
import band.effective.office.tv.core.network.BaseRepository
import band.effective.office.tv.core.network.Resource
import band.effective.office.tv.model.Photo
import band.effective.office.tv.network.synology.SynologyApi
import band.effective.office.tv.network.synology.response.SynologyAuthResponse
import band.effective.office.tv.network.synology.response.SynologyListResponse
import band.effective.office.tv.repository.SynologyPhoto
import com.squareup.moshi.Moshi
import okhttp3.Response
import okhttp3.ResponseBody
import javax.inject.Inject

class SynologyPhotoImpl @Inject constructor(
    private val synologyApi: SynologyApi,
) : SynologyPhoto, BaseRepository() {
    private var dirPath: String = ""
    private var isAuth: Boolean = false
    private var sid: String = ""
    override suspend fun getPhotos(): Resource<List<Photo>> {
        TODO("Not yet implemented")
    }

    override suspend fun changeDir(dir: String): Resource<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun getPhotoPath(folderPath: String): Resource<List<String>> {
        var authResponse: Resource<Boolean>? = null
        if (!isAuth) authResponse = auth()

        if (isAuth) {
            val res: Resource<SynologyListResponse> = safeApiCall {
                synologyApi.getFiles(
                    sid = sid,
                    version = 1,
                    method = "list",
                    folderPath = folderPath
                )
            }
            return when (res) {
                is Resource.Data -> Resource.Data(res.data.data.files.map { it.path })
                is Resource.Error -> Resource.Error(res.error)
                else -> Resource.Loading()
            }
        }

        return if (authResponse is Resource.Error) {
            val message = authResponse.error
            Resource.Error(message)
        } else Resource.Loading()

    }

    override suspend fun auth(): Resource<Boolean> {
        val res: Resource<SynologyAuthResponse> = safeApiCall {
            synologyApi.auth(
                version = 3,
                method = "login",
                login = BuildConfig.synologyLogin,
                password = BuildConfig.synologyPassword,
            )
        }
        return when (res) {
            is Resource.Data -> {
                isAuth = true
                sid = res.data.data?.sid?:""
                Resource.Data(true)
            }
            is Resource.Error -> {
                isAuth = false
                Resource.Error(res.error)
            }
            else -> {
                Resource.Loading()
            }
        }

    }

    override fun convertErrorBody(errorBody: ResponseBody?): BaseError? {
        return try {
            errorBody?.source()?.let {
                val moshiAdapter = Moshi.Builder().build().adapter(BaseError::class.java)
                moshiAdapter.fromJson(it)
            }
        } catch (exception: Exception) {
            throw exception
        }
    }

}