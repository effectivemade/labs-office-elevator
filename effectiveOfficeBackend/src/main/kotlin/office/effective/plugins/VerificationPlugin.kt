package office.effective.plugins


import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import office.effective.features.simpleAuth.service.AuthenticationPipeline
import org.koin.core.context.GlobalContext
import org.slf4j.LoggerFactory

/**
 * Allows to check Authentication plugins automatically.
 * Run every time when receiving input call. Checks Authentication (bearer) header containment
 * */
val VerificationPlugin = createApplicationPlugin(name = "VerificationPlugin") {
    val pluginOn: Boolean = System.getenv("VERIFICATION_PLUGIN_ENABLE").equals("true")
    val logger = LoggerFactory.getLogger(this::class.java)
    logger.info("Verification plugin mode enabled?: $pluginOn")
    logger.info("Verification plugin installed")
    val authenticationPipeline : AuthenticationPipeline = GlobalContext.get().get()

    onCall {
        run {
            if (pluginOn && it.request.path() != "/notifications") {
                val token = it.request.parseAuthorizationHeader()?.render()?.split("Bearer ")?.last() ?: it.respond(
                    HttpStatusCode.Unauthorized
                )
                if (!authenticationPipeline.authenticateToken(token as String)) {
                    logger.info("Verification failed.")
                    logger.trace("Verification failed with token: {}", token)
                    it.response.status(HttpStatusCode.Unauthorized)
                    it.respond("Verification failed.")
                } else {
                    logger.info("Verification succeed.")
                    logger.trace("Verification succeed with token: {}", token)
                }

            }
        }


    }
}
