package office.effective.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import office.effective.features.user.TokenVerifier
import org.koin.core.context.GlobalContext
import io.ktor.server.application.hooks.CallSetup
import io.ktor.server.response.*
import office.effective.features.user.ITokenVerifier


val VerificationPlugin = createApplicationPlugin(name = "VerificationPlugin") {
    val verifier: ITokenVerifier = TokenVerifier()
    val pluginOn: Boolean = System.getenv("VERIFICATION_PLUGIN_ENABLE").equals("true")
    println("Verification plugin mode enabled?: $pluginOn")
    println("==========================[ verification plugin installed ]==========================")
    if (pluginOn) {
        onCall {
            run {

                try {
                    val token = it.request.header("id_token") ?: it.response.status(HttpStatusCode.Forbidden)
                    val email = verifier.isCorrectToken(token as String)
                } catch (ex: Exception) {
                    it.respond("verification error. \nCause by $ex")
                }
            }
        }

    }
}
