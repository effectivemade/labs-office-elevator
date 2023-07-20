import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*

import office.effective.common.utils.UserSession
import office.effective.feature.auth.ITokenVerifier
import office.effective.feature.auth.dto.UserDTO
import office.effective.feature.auth.service.IUserService
import org.koin.core.context.GlobalContext

fun Route.authRoutingFun() {
    val service: IUserService = GlobalContext.get().get()
    val verifier: ITokenVerifier = GlobalContext.get().get()
    authenticate("auth-oauth-google") {
        get("/login") {

        }
        get("/callback") {
            try {

                val principal: OAuthAccessTokenResponse.OAuth2? = call.principal()
                println("================================================================")
                println(principal?.accessToken)
                println("================================================================")

            } catch (ex: Exception) {

                var trace: String = ex.message ?: "There are no message.\n";
                ex.stackTrace.forEach { trace += it.toString() + "\n" }
                trace += "\n"
                trace += ex.cause
                call.respond(trace)

            }

            val token: String = call.receiveText()
            val userEmail = verifier.isCorrectToken(token)
            call.respondText(userEmail)
            call.respondRedirect("http://localhost:8080/callback")
        }
    }
    get("/users/login") {
        val tokenStr = call.request.header("id_token") ?: call.response.status(HttpStatusCode.Forbidden)
        val userDTO: UserDTO = service.getUserByToken(tokenStr as String)
//
    }
    get("/users") {
        var tagStr = call.request.queryParameters["tag"] ?: call.response.status(HttpStatusCode.BadRequest)
        val tokenStr = call.request.header("id_token") ?: call.response.status(HttpStatusCode.Forbidden)
        val users: Set<UserDTO>? = service.getUsersByTag(tagStr as String, tokenStr as String)
        call.respond(users ?: "no such users")
    }
    get("/users/{user_id}") {
        val userId = call.parameters["user_id"] ?: call.response.status(HttpStatusCode.BadRequest)
        val tokenStr = call.request.header("id_token") ?: call.response.status(HttpStatusCode.Forbidden)
        val user = service.getUserById(userId as String, tokenStr as String)
        call.respond(user ?: "No such user. Suggestion: bad id")
    }
    put("/users/{user_id}") {
        val user: UserDTO = call.receive<UserDTO>()
        val tokenStr = call.request.header("id_token") ?: call.response.status(HttpStatusCode.Forbidden)
        call.respond(service.alterUser(user, tokenStr as String))
    }

}
