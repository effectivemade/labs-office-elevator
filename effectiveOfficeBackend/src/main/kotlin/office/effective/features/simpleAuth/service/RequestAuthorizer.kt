package office.effective.features.simpleAuth.service

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.application.*
import org.slf4j.LoggerFactory

/**
 * [ITokenAuthorizer] implementation. Calls oauth2.googleapis.com to verify token
 * */
class RequestVerifier : ITokenAuthorizer {
    val logger = LoggerFactory.getLogger(this::class.java)

    /**
     * Check Google ID Token. Calls oauth2.googleapis.com
     *
     * @param call [String] which contains token to verify
     * @return is token correct
     *
     * @author Kiselev Danil
     * */
    override suspend fun isCorrectToken(call: ApplicationCall): Boolean {
        val client = HttpClient(CIO) {}
        val response: HttpResponse = client.request("https://oauth2.googleapis.com/tokeninfo") {
            url {
                parameters.append("id_token", call)
            }
        }
        if (response.status != HttpStatusCode.OK) {
            return next(call);
        } else {
            logger.info("Request verifier succeed")
            logger.trace("Request verifier succeed with token: {}", call)
            return true
        }
    }

    private var nextHandler: ITokenAuthorizer? = null
}