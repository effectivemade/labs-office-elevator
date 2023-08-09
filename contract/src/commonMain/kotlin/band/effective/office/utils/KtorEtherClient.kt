package band.effective.office.utils

import band.effective.office.network.model.Either
import band.effective.office.network.model.ErrorResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put

object KtorEtherClient {
    val httpClient = HttpClient(CIO) {
        install(KtorEitherPlugin)
    }

    enum class RestMethod { Get, Post, Delete, Put }

    suspend inline fun <reified T> securityResponse(
        urlString: String,
        method: RestMethod = RestMethod.Get,
        block: HttpRequestBuilder.() -> Unit = {},
    ): Either<ErrorResponse, T> =
        try {
            when (method) {
                RestMethod.Get -> httpClient.get(urlString, block)
                RestMethod.Post -> httpClient.post(urlString, block)
                RestMethod.Delete -> httpClient.delete(urlString, block)
                RestMethod.Put -> httpClient.put(urlString, block)
            }.body()
        } catch (e: Exception) {
            Either.Error(ErrorResponse(code = 0, description = e.message ?: "Error"))
        }
}