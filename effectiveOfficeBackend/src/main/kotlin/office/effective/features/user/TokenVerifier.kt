package office.effective.features.user

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import office.effective.config

class TokenVerifier : ITokenVerifier {

    private val webClient = System.getenv("GOOGLE_CLIENT_ID")

    private val verifier: GoogleIdTokenVerifier =
        GoogleIdTokenVerifier.Builder(NetHttpTransport(), GsonFactory()).setAudience(listOf(webClient)).build()

    private val acceptableMailDomain: String =
        config.propertyOrNull("auth.user.emailDomain ")?.getString() ?: "effective.band"

    override fun isCorrectToken(tokenString: String): String {
        var userMail: String? = null
        val token: GoogleIdToken? = verifier.verify(tokenString)

        val payload = token?.payload ?: throw Exception("Token cannot be verified")
        val emailVerified: Boolean = payload.emailVerified
        val hostedDomain = payload.hostedDomain ?: extractDomain(payload.email)

        if ((acceptableMailDomain == hostedDomain) && emailVerified) {
            userMail = payload.email
        }
        return userMail ?: throw Exception("Token wasn't verified")
    }

    private fun extractDomain(email: String?): String {
        email ?: throw Exception("Email is empty")
        return email.split('@').last()
    }

}
