package office.effective

//import io.ktor.server.plugins.contentnegotiation.*
import com.typesafe.config.ConfigFactory
import io.ktor.client.*
import io.ktor.client.engine.apache.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.config.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import office.effective.plugins.*

val config = HoconApplicationConfig(ConfigFactory.load())

val portNumber: Int = config.propertyOrNull("ktor.deployment.port")?.getString()?.toIntOrNull() ?: 8080
val hostId: String = config.propertyOrNull("ktor.deployment.host")?.getString() ?: "0.0.0.0"

val applicationHttpClient = HttpClient(Apache) {
    install(ContentNegotiation) {
        json()
    }
}

fun main() {
    embeddedServer(factory = Netty, port = portNumber, host = hostId, module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureDI()
    install(Authentication) {
        oauth("auth-oauth-google") {
            urlProvider = { "http://localhost:8080/callback" }
            providerLookup = {
                OAuthServerSettings.OAuth2ServerSettings(
                    name = "google",
                    authorizeUrl = "https://accounts.google.com/o/oauth2/auth",
                    accessTokenUrl = "https://accounts.google.com/o/oauth2/token",
                    requestMethod = HttpMethod.Post,
                    clientId = System.getenv("GOOGLE_CLIENT_ID"),
                    clientSecret = System.getenv("GOOGLE_CLIENT_SECRET"),
                    defaultScopes = listOf("https://www.googleapis.com/auth/userinfo.profile", "email"),
                    extraAuthParameters = listOf("access_type" to "offline")
                )
            }
            client = applicationHttpClient
        }
    }
    configureMigration()
    configureSerialization()
    configureSecurity()
    configureRouting()
}
