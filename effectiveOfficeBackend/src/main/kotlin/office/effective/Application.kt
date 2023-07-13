package office.effective

import com.typesafe.config.ConfigFactory
import io.ktor.server.application.*
import io.ktor.server.config.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.sessions.*
import office.effective.common.utils.UserSession
import office.effective.plugins.*


val config = HoconApplicationConfig(ConfigFactory.load())

val portNumber: Int = config.propertyOrNull("ktor.deployment.port")?.getString()?.toIntOrNull() ?: 8080
val hostId: String = config.propertyOrNull("ktor.deployment.host")?.getString() ?: "0.0.0.0"
fun main() {
    embeddedServer(factory = Netty, port = portNumber, host = hostId, module = Application::module)
        .start(wait = true)

}

fun Application.module() {
    configureSerialization()
    configureSecurity()
    configureRouting()
    install(Sessions) {
        cookie<UserSession>("user_session")
    }
}
