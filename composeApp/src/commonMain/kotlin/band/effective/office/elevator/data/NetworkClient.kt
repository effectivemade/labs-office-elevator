package band.effective.office.elevator.data

import io.github.aakira.napier.Napier
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.request
import io.ktor.utils.io.errors.IOException
import kotlinx.serialization.SerializationException

class NetworkClient(enableLogging: Boolean) {

    val ktor = createHttpEngine(enableLogging).config {
        defaultRequest {
            host = "0.0.0.0"
            port = 80
//            host = "51.250.11.188"
//            port = 2105
        }
        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Napier.e("HTTP Client", null, message)
                }
            }
            logger = Logger.DEFAULT
            level = LogLevel.ALL
        }
    }

    suspend inline fun <reified T, reified E> call(
        block: HttpRequestBuilder.() -> Unit,
    ): ApiResponse<T, E> =
        try {
            val response = ktor.request { block() }

            when (val code = response.status.value) {
                in 200..299 -> {
                    ApiResponse.Success(response.body())
                }

                else -> ApiResponse.Error.HttpError(code, null)
            }

        } catch (e: ClientRequestException) {
            ApiResponse.Error.HttpError(e.response.status.value, e.errorBody())
        } catch (e: ServerResponseException) {
            ApiResponse.Error.HttpError(e.response.status.value, e.errorBody())
        } catch (e: IOException) {
            ApiResponse.Error.NetworkError
        } catch (e: SerializationException) {
            ApiResponse.Error.SerializationError
        }

    suspend inline fun <reified E> ResponseException.errorBody(): E? =
        try {
            response.body()
        } catch (e: SerializationException) {
            null
        }
}
