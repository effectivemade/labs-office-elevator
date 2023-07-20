package office.effective.feature.auth

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.gson.GsonFactory

class TokenVerifier : ITokenVerifier {

    private val webClient =
        "27867488691-gc95ln5jad3i84dcmu3dd9ls1s4hvm9c.apps.googleusercontent.com"

    private val verifier: GoogleIdTokenVerifier =
        GoogleIdTokenVerifier.Builder(NetHttpTransport(), GsonFactory()).setAudience(listOf(webClient)).build()

    private val acceptableMailDomain: String = "effective.band"

    override fun isCorrectToken(tokenString: String): String {
        var userMail: String? = null
        val token: GoogleIdToken? = verifier.verify(tokenString)

        val payload = token?.payload ?: throw Exception("Token cannot be verified")
        val emailVerified: Boolean = payload.emailVerified
        val hostedDomain = payload.hostedDomain

        if ((acceptableMailDomain == hostedDomain) && emailVerified) {
            userMail = payload.email
        }
        return userMail ?: throw Exception("Token wasn't verified")
    }

}
