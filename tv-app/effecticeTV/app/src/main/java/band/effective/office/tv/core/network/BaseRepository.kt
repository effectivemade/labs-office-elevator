package band.effective.office.tv.core.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

abstract class BaseRepository {

    suspend fun <T> safeApiCall(apiToBeCalled: suspend () -> Response<T>): Resource<T> {

        return withContext(Dispatchers.IO) {
            try {
                val response: Response<T> = apiToBeCalled()

                if (response.isSuccessful) {
                    Resource.Data(data = response.body()!!)
                } else {

                    val errorResponse: BaseError? = convertErrorBody(response.errorBody())

                    Resource.Error(
                        error = errorResponse?.message ?: "Something went wrong"
                    )
                }

            } catch (e: HttpException) {
                Resource.Error(error = e.message())
            } catch (e: IOException) {
                Resource.Error(e.message ?: "Please check your network connection")
            } catch (e: Exception) {
                Resource.Error(error = "Something went wrong")
            }
        }
    }

    protected abstract fun convertErrorBody(errorBody: ResponseBody?): BaseError?
}

