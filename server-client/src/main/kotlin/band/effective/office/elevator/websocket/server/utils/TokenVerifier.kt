package band.effective.office.elevator.websocket.server.utils

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import com.google.api.client.util.DateTime
import java.time.LocalDateTime

object TokenVerifier {

    private const val androidClient = "627570596451-iaj3hh94lbqognrkgk7vfmtj10c9v69b.apps.googleusercontent.com"
    private const val iosClient = "627570596451-pmtas2pcm6hpkeddpd19uasql1upknun.apps.googleusercontent.com"
    private const val verifyDomain = "effective.band"

    private var verifier: GoogleIdTokenVerifier =
        GoogleIdTokenVerifier.Builder(NetHttpTransport(), GsonFactory())
            .setAudience(listOf(androidClient, iosClient))
            .build()

    fun isCorrectToken(token: String?): Boolean {
        if(token.isNullOrBlank()) return false

        val idToken: GoogleIdToken? = verifier.verify(token)
        return if (idToken != null) {
            val payload: GoogleIdToken.Payload = idToken.payload

            val emailVerified: Boolean = payload.emailVerified
            val hostedDomain = payload.hostedDomain

            val result = (verifyDomain == hostedDomain) && emailVerified
            result
        } else {
            println("Token is incorrect")
            false
        }
    }
}