package office.effective.di

import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.ktorm.database.Database

val databaseDiModule = module {
    //info about db connection.
    single<Database>(named("postgresdb")) {
        Database.connect(
            url = "jdbc:postgresql://localhost:15432/usernames",
            driver = "org.postgresql.Driver",
            user = "postgres",
            password = "test1234567890"
        )
    }
}