package office.effective.plugins

import io.ktor.http.*
import io.ktor.http.auth.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import office.effective.features.user.TokenVerifier
import io.ktor.server.response.*
import office.effective.features.user.ApiKeyVerifier
import office.effective.features.user.ITokenVerifier


val VerificationPlugin = createApplicationPlugin(name = "VerificationPlugin") {
    val verifierOAuth: ITokenVerifier = TokenVerifier()
    val verifierLine = ApiKeyVerifier()
    val pluginOn: Boolean = System.getenv("VERIFICATION_PLUGIN_ENABLE").equals("true")
    println("Verification plugin mode enabled?: $pluginOn")
    println("==========================[ verification plugin installed ]==========================")


    onCall {
        run {
            if (pluginOn) {
                val token = it.request.parseAuthorizationHeader()?.render()?.split("Bearer ")?.last() ?: it.respond(
                    HttpStatusCode.Unauthorized
                )
                var exOAuth: Exception? = null
                var exLine: Exception? = null

                //Checks verification through OAuth
                try {
                    val email = verifierOAuth.isCorrectToken(token as String)
                    exOAuth = null
                } catch (ex: Exception) {
                    exOAuth = ex
                }

                //Checks verification through Line (for tablets usage). Should be replaced by jwt
                try {
                    val line = verifierLine.isCorrectToken(token as String)
                    exLine = null
                } catch (ex: Exception) {
                    exLine = ex
                }

                //If both authentications ways cannot verify header containment, this condition must throw 401 Unauthorised
                if (exOAuth != null && exLine != null) {
                    it.response.status(HttpStatusCode.Unauthorized)
                    it.respond("verification error. \nCause by:\nOAuth exception: $exOAuth\nLine exception: $exLine")
                }
            }
        }

    }
}
