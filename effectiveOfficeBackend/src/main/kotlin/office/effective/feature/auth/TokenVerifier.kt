package office.effective.common.utils

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.gson.GsonFactory

object TokenVerifier {

    private const val webClient =
        "27867488691-gc95ln5jad3i84dcmu3dd9ls1s4hvm9c.apps.googleusercontent.com"

    private const val verifyDomain = "effective.band"

    private val verifier: GoogleIdTokenVerifier =
        GoogleIdTokenVerifier.Builder(NetHttpTransport(), GsonFactory()).setAudience(listOf(webClient)).build()

    fun isCorrectToken(tokenString: String): GoogleIdToken? {
        return verifier.verify(tokenString)
    }

}
