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
    val authenticationPipeline: AuthenticationPipeline = GlobalContext.get().get()

    onCall { call ->
        run {
            if (pluginOn && call.request.path() != "/notifications") {
                val token = call.request.parseAuthorizationHeader()?.render()?.split("Bearer ")?.last() ?: run {
                    logger.info("Verification failed. Cannot find auth token")
                    call.respond(
                        HttpStatusCode.Unauthorized
                    )
                    return@onCall
                }
                if (!authenticationPipeline.authenticateToken(token as String)) {
                    logger.info("Verification failed.")
                    logger.trace("Verification failed with token: {}", token)
                    call.response.status(HttpStatusCode.Unauthorized)
                    call.respond("Verification failed.")
                } else {
                    logger.info("Verification succeed.")
                    logger.trace("Verification succeed with token: {}", token)
                }

            }
        }


    }
}
