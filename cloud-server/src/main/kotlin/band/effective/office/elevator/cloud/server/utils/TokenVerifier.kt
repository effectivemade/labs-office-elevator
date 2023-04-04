package band.effective.office.elevator.cloud.server.utils

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.gson.GsonFactory

object TokenVerifier {

    private const val webClient =
        "726357293621-s4lju93oibotmefghoh3b3ucckalh933.apps.googleusercontent.com"
    private const val verifyDomain = "effective.band"

    private var verifier: GoogleIdTokenVerifier =
        GoogleIdTokenVerifier.Builder(NetHttpTransport(), GsonFactory())
            .setAudience(listOf(webClient)).build()

    fun isCorrectToken(token: String?): Boolean {
        if (token.isNullOrBlank()) return false

        val idToken: GoogleIdToken? = verifier.verify(token)
        return if (idToken != null) {
            val payload: GoogleIdToken.Payload = idToken.payload

            val emailVerified: Boolean = payload.emailVerified
            val hostedDomain = payload.hostedDomain

            val result = (verifyDomain == hostedDomain) && emailVerified
            result
        } else {
            println("Google ID token is not verified")
            false
        }
    }
}