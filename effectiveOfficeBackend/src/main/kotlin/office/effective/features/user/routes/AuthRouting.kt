import io.github.smiley4.ktorswaggerui.dsl.get
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import office.effective.common.swagger.SwaggerDocument
import office.effective.common.utils.DatabaseTransactionManager

import office.effective.features.user.ITokenVerifier
import office.effective.features.user.routes.swagger.callback
import office.effective.features.user.routes.swagger.login
import org.koin.core.context.GlobalContext

fun Route.authRoutingFun() {
    val verifier: ITokenVerifier = GlobalContext.get().get()
    val transactionManager: DatabaseTransactionManager = GlobalContext.get().get()

    authenticate("auth-oauth-google") {
        get("/login", SwaggerDocument.login()) {

        }
        get("/callback", SwaggerDocument.callback()) {
            try {
                val principal: OAuthAccessTokenResponse.OAuth2 =
                    call.principal() ?: throw Exception("Token cannot be verified")
                val res = transactionManager.useTransaction({
                    verifier.isCorrectToken(
                        principal.extraParameters["id_token"] ?: ""
                    )
                })
                call.respondText(res)

            } catch (ex: Exception) {
                call.respond("Exception: ${ex.message ?: "There are no message.\n"} ${ex.stackTrace.toList()} \n ${ex.cause}")
            }
            call.respondRedirect("http://localhost:8080/callback")
        }
    }
}
