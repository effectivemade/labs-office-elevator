package office.effective.di

import org.koin.dsl.module
import org.ktorm.database.Database

val databaseDiModule = module {
    //info about db connection.
    single<Database>(createdAtStart = true) {
        Database.connect(
            url = "jdbc:postgresql://localhost:15432/effectiveOfficeBackendDB",
            driver = "org.postgresql.Driver",
            user = "postgres",
            password = "test1234567890"
        )
    }
}