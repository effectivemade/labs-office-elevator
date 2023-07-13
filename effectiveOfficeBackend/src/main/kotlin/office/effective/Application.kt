package office.effective

import com.typesafe.config.ConfigFactory
import io.ktor.server.application.*
import io.ktor.server.config.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import liquibase.Contexts
import liquibase.LabelExpression
import liquibase.Liquibase
import liquibase.database.DatabaseFactory
import liquibase.database.jvm.JdbcConnection
import liquibase.resource.ClassLoaderResourceAccessor
import office.effective.plugins.configureRouting
import office.effective.plugins.configureSecurity
import office.effective.plugins.configureSerialization
import org.postgresql.core.ConnectionFactory.openConnection
import java.sql.Connection
import java.sql.DriverManager


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

    environment.monitor.subscribe(ApplicationStarted) {
        val connection = DriverManager.getConnection(
            "jdbc:postgresql://localhost:15432/effectiveOfficeBackendDB",
            "postgres",
            "test1234567890"
        )
        val databaseConnection = JdbcConnection(connection)
        val liquibase = Liquibase("src/main/resources/changelog/changelog.yaml", ClassLoaderResourceAccessor(),
            DatabaseFactory.getInstance().findCorrectDatabaseImplementation(databaseConnection))
        liquibase.update("")  // Empty string applies all changes

        liquibase.database.close()
    }
}
