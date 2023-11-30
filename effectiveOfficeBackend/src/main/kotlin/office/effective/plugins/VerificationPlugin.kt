package office.effective.plugins


import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import office.effective.features.simpleAuth.service.AuthorizationPipeline
import org.koin.core.context.GlobalContext
import org.slf4j.LoggerFactory

/**
 * Allows to check Authentication plugins automatically.
 * Run every time when receiving input call. Checks Authentication (bearer) header containment
 * */
val VerificationPlugin = createApplicationPlugin(name = "VerificationPlugin") {
    val pluginOn: Boolean = System.getenv("VERIFICATION_PLUGIN_ENABLE").equals("true") //
    val logger = LoggerFactory.getLogger(this::class.java)
    logger.info("Verification plugin mode enabled?: $pluginOn")
    logger.info("Verification plugin installed")
    val authenticationPipeline: AuthorizationPipeline = GlobalContext.get().get()

    onCall { call ->
        run {
            if (pluginOn && call.request.path() != "/notifications") {
                if (!authenticationPipeline.authorize(call)) {
                    logger.info("Verification failed.")
                    call.response.status(HttpStatusCode.Unauthorized)
                    call.respond("Verification failed.")
                } else {
                    logger.info("Verification succeed.")
                }

            }
        }
    }
}
