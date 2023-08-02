package office.effective.workspace

import io.ktor.server.testing.*
import junit.framework.TestCase
import liquibase.Liquibase
import liquibase.database.DatabaseFactory
import liquibase.database.jvm.JdbcConnection
import liquibase.resource.ClassLoaderResourceAccessor
import office.effective.common.exception.InstanceNotFoundException
import office.effective.config
import office.effective.features.workspace.repository.WorkspaceTagEntity
import office.effective.features.workspace.repository.workspaceTags
import org.junit.Test
import org.ktorm.database.Database
import org.ktorm.dsl.eq
import org.ktorm.entity.find
import java.sql.DriverManager

class WorkspaceTest {
    private val url: String =
        "jdbc:h2:mem:effectiveOfficeBackendD;DB_CLOSE_DELAY=-1;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;DEFAULT_NULL_ORDERING=HIGH"//config.propertyOrNull("test.database.url")?.getString() ?: "jdbc:h2:mem:effectiveOfficeBackendDB"

    val changelogFile: String = config.propertyOrNull("liquibase.changelogFile")
        ?.getString() ?: "changelog/changelog-master.yaml"
    val defaultSchemaName: String = config.propertyOrNull("liquibase.defaultSchemaName")
        ?.getString() ?: "public"

    val db = Database.connect(
        url = url,
        driver = "org.h2.Driver",
    )

    @Test
    fun testRoot() = testApplication {
        val connection = DriverManager.getConnection(
            url
        )
        val databaseConnection = JdbcConnection(connection)
        val database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(databaseConnection)
        val liquibase = Liquibase(changelogFile, ClassLoaderResourceAccessor(), database)
        liquibase.update("")
        val tagEntity: WorkspaceTagEntity = db.workspaceTags.find { it.name eq "meeting" }
            ?: throw InstanceNotFoundException(WorkspaceTagEntity::class, "Workspace tag  not found")
        TestCase.assertEquals("meeting", tagEntity.name)
        liquibase.database.close()
    }
}
