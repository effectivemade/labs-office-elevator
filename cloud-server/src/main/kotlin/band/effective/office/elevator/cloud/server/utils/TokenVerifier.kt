package band.effective.office.elevator.cloud.server.utils

import band.effective.office.common.utils.PropertiesUtil
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.gson.GsonFactory

object TokenVerifier {

    private val androidClient = PropertiesUtil.read("GOOGLE_CLOUD_ANDROID_CLIENT")
    private val iosClient = PropertiesUtil.read("GOOGLE_CLOUD_IOS_CLIENT")
    private val verifyDomain = PropertiesUtil.read("OFFICE_ELEVATOR_ACCEPTED_DOMAIN")

    private var verifier: GoogleIdTokenVerifier =
        GoogleIdTokenVerifier.Builder(NetHttpTransport(), GsonFactory())
            .setAudience(listOf(androidClient, iosClient))
            .build()

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
            println("Token is incorrect")
            false
        }
    }
}