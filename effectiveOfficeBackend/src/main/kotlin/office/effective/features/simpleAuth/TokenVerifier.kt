package office.effective.features.simpleAuth

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
     * Check Google ID Token using google library
     *
     * @param tokenString [String] which contains token to verify
     * @author Kiselev Danil
     * @throws Exception("Token wasn't verified by Google") if token does not contain payload
     * @return is token correct
     *
     * @author Kiselev Danil
     * */
    override suspend fun isCorrectToken(tokenString: String): Boolean {
        var userMail: String? = null
        val token: GoogleIdToken? = verifier.verify(tokenString)

        val payload = token?.payload ?: throw Exception("Token wasn't verified by Google")
        val emailVerified: Boolean = payload.emailVerified
        val hostedDomain = payload.hostedDomain ?: extractDomain(payload.email)
        if ((acceptableMailDomain == hostedDomain) && emailVerified) {
            userMail = payload.email
        }

        if(userMail.isNullOrBlank()){
            return next(tokenString)
        }
        else {
            return true
        }

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

    private var nextHandler: ITokenVerifier? = null;
    override fun setNext(handler: ITokenVerifier?) {
        this.nextHandler = handler
    }


    override suspend fun next(tokenString: String): Boolean {
        return nextHandler?.isCorrectToken(tokenString) ?: return false;
    }
}
