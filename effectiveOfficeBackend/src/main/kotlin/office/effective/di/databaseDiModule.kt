package office.effective.di

import org.koin.dsl.module
import org.ktorm.database.Database

val databaseDiModule = module(createdAtStart = true) {
    //info about db connection.
    single<Database> {
        Database.connect(
            url = "jdbc:postgresql://localhost:15432/effectiveOfficeBackendDB",
            driver = "org.postgresql.Driver",
            user = "postgres",
            password = "test1234567890"
        )
    }
}