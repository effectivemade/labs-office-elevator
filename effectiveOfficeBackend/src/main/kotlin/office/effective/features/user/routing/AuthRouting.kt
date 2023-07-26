import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

import office.effective.features.user.ITokenVerifier
import org.koin.core.context.GlobalContext

fun Route.authRoutingFun() {

    val verifier: ITokenVerifier = GlobalContext.get().get()

    authenticate("auth-oauth-google") {
        get("/login") {

        }
        get("/callback") {
            try {
                val principal: OAuthAccessTokenResponse.OAuth2? = call.principal()
                call.respondText(verifier.isCorrectToken(principal!!.extraParameters["id_token"] ?: ""))

            } catch (ex: Exception) {
                call.respond("Exception: ${ex.message ?: "There are no message.\n"} ${ex.stackTrace.toList()} \n ${ex.cause}")
            }
            call.respondRedirect("http://localhost:8080/callback")
        }
    }

}
