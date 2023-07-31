import io.github.smiley4.ktorswaggerui.dsl.get
import io.github.smiley4.ktorswaggerui.dsl.put
import io.github.smiley4.ktorswaggerui.dsl.route
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import office.effective.common.swagger.SwaggerDocument
import office.effective.common.utils.DatabaseTransactionManager

import office.effective.features.user.ITokenVerifier
import office.effective.features.user.converters.UserDTOModelConverter
import office.effective.features.user.dto.UserDTO
import office.effective.features.user.repository.UserRepository
import office.effective.features.user.routes.swagger.alterUser
import office.effective.features.user.routes.swagger.returnUserByEmail
import office.effective.features.user.routes.swagger.returnUserById
import office.effective.features.user.routes.swagger.returnUsers
import office.effective.features.user.service.IUserService
import office.effective.features.workspace.repository.WorkspaceTags
import org.koin.core.context.GlobalContext

fun Route.authRoutingFun() {
    val verifier: ITokenVerifier = GlobalContext.get().get()
    val transactionManager: DatabaseTransactionManager = GlobalContext.get().get()

    authenticate("auth-oauth-google") {
        get("/login") {

        }
        get("/callback") {
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
