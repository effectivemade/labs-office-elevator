package office.effective

import com.typesafe.config.ConfigFactory
import io.ktor.server.application.*
import io.ktor.server.config.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
<<<<<<< HEAD
import office.effective.di.databaseDiModule
import office.effective.plugins.configureMigration
import office.effective.plugins.configureRouting
import office.effective.plugins.configureSecurity
import office.effective.plugins.configureSerialization
import org.koin.ktor.plugin.Koin

=======
import office.effective.plugins.configureMigration
import office.effective.plugins.configureRouting
import office.effective.plugins.configureSecurity
import office.effective.plugins.configureSerialization
>>>>>>> develop

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
    install(Koin) {
        modules(databaseDiModule)
    }
}
