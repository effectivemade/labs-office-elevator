package band.effective.office.elevator.data.database.di

import band.effective.office.elevator.Database
import band.effective.office.elevator.data.database.DriverFactory
import org.koin.dsl.module

//val driverFactoryModule = module {
//
//}


val databaseModule = module {
    single { DriverFactory().createDriver() }
    factory { Database(get()) }
}