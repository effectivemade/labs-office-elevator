package office.effective.plugins

import io.ktor.server.application.*
import liquibase.Liquibase
import liquibase.database.DatabaseFactory
import liquibase.database.jvm.JdbcConnection
import liquibase.resource.ClassLoaderResourceAccessor
import java.sql.DriverManager

fun Application.configureMigration() {
    environment.monitor.subscribe(ApplicationStarted) {
        val connection = DriverManager.getConnection(
            "jdbc:postgresql://localhost:15432/effectiveOfficeBackendDB",
            "postgres",
            "test1234567890"
        )
        val databaseConnection = JdbcConnection(connection)
        val database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(databaseConnection)
        database.defaultSchemaName = "public"
        val liquibase = Liquibase("changelog/changelog.yaml", ClassLoaderResourceAccessor(),
            database)
        liquibase.update("")
        liquibase.database.close()
    }
}