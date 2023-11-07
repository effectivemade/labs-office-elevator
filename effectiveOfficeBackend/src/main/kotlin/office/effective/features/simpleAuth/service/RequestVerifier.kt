package office.effective.features.simpleAuth.service

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import office.effective.features.simpleAuth.ITokenVerifier

class RequestVerifier : ITokenVerifier {
    override suspend fun isCorrectToken(tokenString: String): String {
        val client = HttpClient(CIO) {}
        val response: HttpResponse = client.request("https://oauth2.googleapis.com/tokeninfo") {
            url {
                parameters.append("id_token", tokenString)
            }
        }
        if (response.status != HttpStatusCode.OK) {
            throw Exception("Token wasn't verified")
        }
        return "Everything fine;"
    }
}