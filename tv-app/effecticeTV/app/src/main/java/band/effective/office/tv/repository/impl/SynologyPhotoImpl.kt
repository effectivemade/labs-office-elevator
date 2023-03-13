package band.effective.office.tv.repository.impl

import band.effective.office.tv.BuildConfig
import band.effective.office.tv.core.network.BaseError
import band.effective.office.tv.core.network.BaseRepository
import band.effective.office.tv.core.network.Resource
import band.effective.office.tv.model.Photo
import band.effective.office.tv.network.synology.SynologyApi
import band.effective.office.tv.network.synology.response.SynologyAuthResponse
import band.effective.office.tv.repository.SynologyPhoto
import com.squareup.moshi.Moshi
import okhttp3.ResponseBody
import javax.inject.Inject

class SynologyPhotoImpl @Inject constructor(
    private val synologyApi: SynologyApi,
) : SynologyPhoto, BaseRepository() {
    override suspend fun getPhotos(): Resource<List<Photo>> {
        TODO("Not yet implemented")
    }

    override suspend fun changeDir(dir: String): Resource<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun getFiles(): Resource<Boolean> {
        TODO("Not yet implemented")
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
        return when(res) {
            is Resource.Data -> Resource.Data(true)
            is Resource.Error -> Resource.Error(res.error)
            else -> Resource.Loading()
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