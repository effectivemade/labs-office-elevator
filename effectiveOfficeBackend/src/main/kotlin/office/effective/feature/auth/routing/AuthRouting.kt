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
    authenticate("auth-oauth-google") {
        get("/login") {

        }
        get("/callback") {
            try {


//        val redirects = mutableMapOf<String, String>()
                val principal: OAuthAccessTokenResponse.OAuth2? = call.principal()
//                val a: OpenID
//                call.sessions.set(UserSession(principal!!.state!!, principal.accessToken))
                println("================================================================")
                println(principal?.accessToken)
                println("================================================================")
//            val token = call.request.header("user_session") ?: call.response.status(HttpStatusCode.Forbidden)
//            val googleToken: GoogleIdToken? = verifier.isCorrectToken(token as String)
//            googleToken ?: call.response.status(HttpStatusCode.Forbidden)
//            call.sessions.set(UserSession(token, googleToken?.payload?.email))

                //TODO: call a service/percistance for this user
            } catch (ex: Exception) {
                var trace: String = ex.message ?: "There are no message.\n";
                ex.stackTrace.forEach { trace += it.toString() + "\n" }
                trace += "\n"
                trace += ex.cause
                call.respond(trace)
            }
            //Get the idToken:

            //Use the id token:
            val verifier: ITokenVerifier = GlobalContext.get().get()
            val token: String = call.receiveText()
            val userEmail = verifier.isCorrectToken(token)
            call.respondText(userEmail)
//        val redirect = redirects[principal.state!!]
//        call.respondRedirect(redirect!!)
            call.respondRedirect("http://localhost:8080/callback")
        }
    }
    get("/user/login") {
        val tokenStr = call.request.header("id_token") ?: call.response.status(HttpStatusCode.Forbidden)
        val userDTO: UserDTO = service.getUserByToken(tokenStr as String)
//
    }

}
