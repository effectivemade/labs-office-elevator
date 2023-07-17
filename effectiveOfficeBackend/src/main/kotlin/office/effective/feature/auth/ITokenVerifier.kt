package office.effective.feature.auth

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken

interface ITokenVerifier {
    fun isCorrectToken(tokenString: String): String;
}