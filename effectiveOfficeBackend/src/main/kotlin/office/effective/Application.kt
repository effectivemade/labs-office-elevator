package office.effective

import com.typesafe.config.ConfigFactory
import io.ktor.client.*
import io.ktor.client.engine.apache.*
import io.ktor.client.engine.cio.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.config.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import office.effective.common.utils.UserSession
import office.effective.di.databaseDiModule
import office.effective.plugins.configureMigration
import office.effective.plugins.configureRouting
import office.effective.plugins.configureSecurity
import office.effective.plugins.configureSerialization
import org.koin.ktor.plugin.Koin

val config = HoconApplicationConfig(ConfigFactory.load())

val portNumber: Int = config.propertyOrNull("ktor.deployment.port")?.getString()?.toIntOrNull() ?: 8080
val hostId: String = config.propertyOrNull("ktor.deployment.host")?.getString() ?: "0.0.0.0"
fun main() {
    embeddedServer(factory = Netty, port = portNumber, host = hostId, module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureMigration()
    configureSerialization()
    configureSecurity()
    configureRouting()

    install(Sessions) {
        // TODO: Cookies
        header<UserSession>("user_session")
    }
    authentication {
        oauth("auth-oauth-google") {
            urlProvider = { "http://localhost:8080/callback" }
            providerLookup = {
                OAuthServerSettings.OAuth2ServerSettings(
                    name = "google",
                    authorizeUrl = "https://accounts.google.com/o/oauth2/auth",
                    accessTokenUrl = "https://accounts.google.com/o/oauth2/token",
                    requestMethod = HttpMethod.Post,
                    clientId = "27867488691-gc95ln5jad3i84dcmu3dd9ls1s4hvm9c.apps.googleusercontent.com", /*System.getenv("GOOGLE_CLIENT_ID")*/
                    clientSecret = "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",//System.getenv("GOOGLE_CLIENT_SECRET"),
                    defaultScopes = listOf("https://www.googleapis.com/auth/userinfo.profile")
                )
            }
            client = HttpClient(Apache)
        }
    }
    install(Koin) {
        modules(databaseDiModule)
    }
}
