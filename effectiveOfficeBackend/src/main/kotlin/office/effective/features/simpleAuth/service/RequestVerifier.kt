package office.effective.features.simpleAuth.service

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import office.effective.features.simpleAuth.ITokenVerifier
import org.slf4j.LoggerFactory

/**
 * [ITokenVerifier] implementation. Calls oauth2.googleapis.com to verify token
 * */
class RequestVerifier : ITokenVerifier {
    val logger = LoggerFactory.getLogger(this::class.java)

    /**
     * Check Google ID Token. Calls oauth2.googleapis.com
     *
     * @param tokenString [String] which contains token to verify
     * @return is token correct
     *
     * @author Kiselev Danil
     * */
    override suspend fun isCorrectToken(tokenString: String): Boolean {
        val client = HttpClient(CIO) {}
        val response: HttpResponse = client.request("https://oauth2.googleapis.com/tokeninfo") {
            url {
                parameters.append("id_token", tokenString)
            }
        }
        if (response.status != HttpStatusCode.OK) {
            return next(tokenString);
        } else {
            logger.info("Request verifier succeed")
            logger.trace("Request verifier succeed with token: {}", tokenString)
            return true
        }
    }

    private var nextHandler: ITokenVerifier? = null;
    override fun setNext(handler: ITokenVerifier?) {
        this.nextHandler = handler
    }

    override suspend fun next(tokenString: String): Boolean {
        logger.info("Token verifier failed")
        logger.trace("Token verifier with token: {}", tokenString)
        return nextHandler?.isCorrectToken(tokenString) ?: return false;
    }
}