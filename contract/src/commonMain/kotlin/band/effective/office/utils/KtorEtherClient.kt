package band.effective.office.utils

import band.effective.office.network.model.Either
import band.effective.office.network.model.ErrorResponse
import effective_office.contract.BuildConfig
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put

object KtorEtherClient {
    var token = BuildConfig.apiKey
    val httpClient by lazy {
        HttpClient(CIO) {
            install(KtorEitherPlugin)
            install(Auth) {
                bearer {
                    loadTokens {
                        BearerTokens(token, "")
                    }
                }
            }
            install(HttpTimeout) {
                requestTimeoutMillis = 10000
            }
        }
    }

    enum class RestMethod { Get, Post, Delete, Put }

    suspend inline fun <reified T> securityResponse(
        urlString: String,
        method: RestMethod = RestMethod.Get,
        client: HttpClient = httpClient,
        block: HttpRequestBuilder.() -> Unit = {},
    ): Either<ErrorResponse, T> =
        try {
            when (method) {
                RestMethod.Get -> client.get(urlString, block)
                RestMethod.Post -> client.post(urlString, block)
                RestMethod.Delete -> client.delete(urlString, block)
                RestMethod.Put -> client.put(urlString, block)
            }.body()
        } catch (e: Exception) {
            Either.Error(ErrorResponse(code = 0, description = e.message ?: "Error"))
        }
}