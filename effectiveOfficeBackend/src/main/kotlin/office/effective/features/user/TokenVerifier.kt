package office.effective.features.user

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import office.effective.config

/**
 * Implementation of [ITokenVerifier]. Checks GoogleIdTokens
 * */
class TokenVerifier : ITokenVerifier {

    private val webClient = System.getenv("GOOGLE_CLIENT_ID")

    private val verifier: GoogleIdTokenVerifier =
        GoogleIdTokenVerifier.Builder(NetHttpTransport(), GsonFactory()).build()

    private val acceptableMailDomain: String =
        config.propertyOrNull("auth.user.emailDomain ")?.getString() ?: "effective.band"

    /**
     * Check Google Id Token from input line. Returns email
     *
     * @param tokenString [String] which contains token to verify
     * @author Kiselev Danil
     * @throws Exception("Token cannot be verified") if token does not contains payload
     * @throws Exception("Token wasn't verified") if method can't extract email
     * @return user email
     *
     * @author Kiselev Danil
     * */
    override fun isCorrectToken(tokenString: String): String {
        var userMail: String? = null
        val token: GoogleIdToken? = verifier.verify(tokenString)

        val payload = token?.payload ?: throw Exception("Token wasn't verified by Google")
        val emailVerified: Boolean = payload.emailVerified
        val hostedDomain = payload.hostedDomain ?: extractDomain(payload.email)

        if ((acceptableMailDomain == hostedDomain) && emailVerified) {
            userMail = payload.email
        }
        return userMail ?: throw Exception("Token wasn't verified")
    }

    /**
     * @param email - [String] from Bearer auth header, which must contains email
     * @throws Exception("Email is empty") if input line is null
     * @return [String], which contains email address
     *
     * @author Kiselev Danil
     * */
    private fun extractDomain(email: String?): String {
        email ?: throw Exception("Email is empty")
        return email.split('@').last()
    }

}
