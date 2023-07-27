package office.effective.common.utils

import office.effective.config
import org.koin.dsl.module
import org.ktorm.database.Database

val commonDiModule = module(createdAtStart = true) {
    val url: String = config.propertyOrNull("database.url")
        ?.getString() ?: "jdbc:postgresql://localhost:15432/effectiveOfficeBackendDB"
    val username: String = config.propertyOrNull("database.username")?.getString() ?: "postgres"
    val password: String = config.propertyOrNull("database.password")?.getString() ?: "test1234567890"

    single<Database> {
        Database.connect(
            url = url,
            driver = "org.postgresql.Driver",
            user = username,
            password = password
        )
    }
}
