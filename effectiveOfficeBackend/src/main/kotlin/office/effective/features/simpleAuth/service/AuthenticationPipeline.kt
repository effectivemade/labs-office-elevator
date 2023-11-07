package office.effective.features.simpleAuth.service

import office.effective.features.simpleAuth.ApiKeyVerifier
import office.effective.features.simpleAuth.TokenVerifier

class AuthenticationPipeline {

    val tokenVerifier = TokenVerifier()
    val requestVerifier = RequestVerifier()
    val apiKeyVerifier = ApiKeyVerifier()

    suspend fun authenticateToken(token: String): Boolean {
        tokenVerifier.setNext(requestVerifier)
        requestVerifier.setNext(apiKeyVerifier)
        return tokenVerifier.isCorrectToken(token);
    }
}
