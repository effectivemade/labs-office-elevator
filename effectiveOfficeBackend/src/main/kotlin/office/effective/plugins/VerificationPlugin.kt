package office.effective.plugins

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import office.effective.features.simpleAuth.TokenVerifier
import io.ktor.server.response.*
import office.effective.features.simpleAuth.ApiKeyVerifier
import office.effective.features.simpleAuth.ITokenVerifier
import office.effective.features.simpleAuth.service.RequestVerifier
import org.slf4j.LoggerFactory

/**
 * Allows to check Authentication plugins automatically.
 * Run every time when receiving input call. Checks Authentication (bearer) header containment
 * */
val VerificationPlugin = createApplicationPlugin(name = "VerificationPlugin") {
    val verifierOAuth: ITokenVerifier = TokenVerifier()
    val verifierLine = ApiKeyVerifier()
    val pluginOn: Boolean = System.getenv("VERIFICATION_PLUGIN_ENABLE").equals("true")
    val logger = LoggerFactory.getLogger(this::class.java)
    logger.info("Verification plugin mode enabled?: $pluginOn")
    logger.info("Verification plugin installed")

    onCall {
        run {
            if (pluginOn && it.request.path() != "/notifications") {
                val token = it.request.parseAuthorizationHeader()?.render()?.split("Bearer ")?.last() ?: it.respond(
                    HttpStatusCode.Unauthorized
                )
                var exOAuth: Exception? = null;
                var exLine: Exception? = null;
                var exRequest: Exception? = null;

//                Checks verification through OAuth
                try {
                    verifierOAuth.isCorrectToken(token as String)
                    exOAuth = null
                } catch (ex: Exception) {
                    logger.debug("OAuth verification failed")
                    exOAuth = ex
                }

                //Checks verification through Line (for tablets usage). Should be replaced by jwt
                try {
                    verifierLine.isCorrectToken(token as String)
                    exLine = null
                } catch (ex: Exception) {
                    logger.debug("Token verification failed")
                    exLine = ex
                }


                try {
                    RequestVerifier().isCorrectToken(token as String)
                    exLine = null
                } catch (ex: Exception) {
                    logger.debug("Token verification failed")
                    exRequest = ex
                }


                //If both authentications ways cannot verify header containment, this condition must throw 401 Unauthorised
                if (exOAuth != null && exLine != null && exRequest != null) {
                    logger.info("Verification error. \nOAuth exception: ", exOAuth)
                    logger.info("Verification error. \nLine exception: ", exLine)
                    it.response.status(HttpStatusCode.Unauthorized)
                    it.respond("Verification error. \nCause by:\nOAuth exception: $exOAuth\nLine exception: $exLine")
                }
            }
        }

    }
}
