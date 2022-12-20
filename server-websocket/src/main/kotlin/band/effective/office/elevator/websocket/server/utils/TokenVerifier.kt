package band.effective.office.elevator.websocket.server.utils

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.gson.GsonFactory

object TokenVerifier {

    const val androidClient = "627570596451-iaj3hh94lbqognrkgk7vfmtj10c9v69b.apps.googleusercontent.com"
    const val iosClient = "627570596451-pmtas2pcm6hpkeddpd19uasql1upknun.apps.googleusercontent.com"
    const val verifyDomain = "effective.band"

    var verifier: GoogleIdTokenVerifier =
        GoogleIdTokenVerifier.Builder(NetHttpTransport(), GsonFactory())
            .setAudience(listOf(androidClient, iosClient))
            .build()

    fun isCorrectToken(token: String?): Boolean {
        val idToken: GoogleIdToken? = verifier.verify(token)
        if (idToken != null) {
            val payload: GoogleIdToken.Payload = idToken.payload

            // Print user identifier
            val userId: String = payload.subject
            println("User ID: $userId")

            // Get profile information from payload
            val email: String = payload.email
            val emailVerified: Boolean = payload.emailVerified
            val name = payload["name"] as String
            val pictureUrl = payload["picture"] as String
            val locale = payload["locale"] as String
            val familyName = payload["family_name"] as String
            val givenName = payload["given_name"] as String
            val hostedDomain = payload.hostedDomain

            println(
                "\nemail: $email\n" +
                        "emailVerified: $emailVerified\n" +
                        "name: $name\n" +
                        "pictureUrl: $pictureUrl\n" +
                        "locale: $locale\n" +
                        "familyName: $familyName\n" +
                        "givenName: $givenName\n" +
                        "hostedDomain: $hostedDomain\n"
            )

            val result = (verifyDomain == hostedDomain) && emailVerified
            return result
        } else {
            println("Token is incorrect")
            return false
        }

    }
}