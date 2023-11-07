package office.effective.features.simpleAuth.service

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import office.effective.features.simpleAuth.ITokenVerifier
/**
 * [ITokenVerifier] implementation. Calls oauth2.googleapis.com to verify token
 * */
class RequestVerifier : ITokenVerifier {

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
        }
        return true
    }
    private var nextHandler: ITokenVerifier? = null;
    override fun setNext(handler: ITokenVerifier?) {
        this.nextHandler = handler
    }

    override suspend fun next(tokenString: String): Boolean {
        return nextHandler?.isCorrectToken(tokenString) ?: return false;
    }
}