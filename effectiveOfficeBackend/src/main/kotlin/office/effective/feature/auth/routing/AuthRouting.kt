import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import office.effective.common.utils.TokenVerifier
import office.effective.common.utils.UserSession

fun Route.authRoutingFun() {

    post("/post_token") {
        try {
            val token = call.request.header("user_session") ?: call.response.status(HttpStatusCode.Forbidden)
            val googleToken: GoogleIdToken? = TokenVerifier.isCorrectToken(token as String)
            googleToken ?: call.response.status(HttpStatusCode.Forbidden)
            call.sessions.set(UserSession(token, googleToken?.payload?.email))
        } catch (ex: Exception) {
            var trace: String = ex.message ?: "There are no message.\n";
            ex.stackTrace.forEach { trace += it.toString() + "\n" }
            trace += "                           \n"
            trace += ex.cause
            call.respond(trace)
        }
    }

    post("/logout") {
        call.sessions.clear<UserSession>()
    }

    get("/profile") {
        val sessionInfo: UserSession? = call.sessions.get<UserSession>()
        call.respond(sessionInfo?.toString() ?: "No such data")
    }

}


