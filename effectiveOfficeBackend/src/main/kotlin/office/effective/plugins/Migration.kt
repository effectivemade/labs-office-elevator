package office.effective.plugins

import io.ktor.server.application.*
import liquibase.Liquibase
import liquibase.database.DatabaseFactory
import liquibase.database.jvm.JdbcConnection
import liquibase.resource.ClassLoaderResourceAccessor
import office.effective.config
import java.sql.DriverManager

fun Application.configureMigration() {
    val url: String = config.propertyOrNull("database.url")
        ?.getString() ?: "jdbc:postgresql://localhost:15432/effectiveOfficeBackendDB"
    val username: String = config.propertyOrNull("database.username")?.getString() ?: "postgres"
    val password: String = config.propertyOrNull("database.password")?.getString() ?: "test1234567890"

    val changelogFile: String = config.propertyOrNull("liquibase.changelogFile")
        ?.getString() ?: "changelog/changelog-master.yaml"
    val defaultSchemaName: String = config.propertyOrNull("liquibase.defaultSchemaName")
        ?.getString() ?: "public"

    val connection = DriverManager.getConnection(
        url,
        username,
        password
    )
    val databaseConnection = JdbcConnection(connection)
    val database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(databaseConnection)
    database.defaultSchemaName = defaultSchemaName
    val liquibase = Liquibase(changelogFile, ClassLoaderResourceAccessor(), database)
    liquibase.update("")
    liquibase.database.close()
}