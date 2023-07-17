package band.effective.office.elevator.data.database.di

import band.effective.office.elevator.Database
import band.effective.office.elevator.data.database.DriverFactory
import org.koin.dsl.module

val databaseModule = module {
    single { DriverFactory(get()).createDriver() }
    factory { Database(get()) }
}