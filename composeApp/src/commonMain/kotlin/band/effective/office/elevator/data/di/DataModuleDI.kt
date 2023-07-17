package band.effective.office.elevator.data.di

import band.effective.office.elevator.data.NetworkClient
import band.effective.office.elevator.data.database.DBSource
import band.effective.office.elevator.data.database.DBSourceImpl
import org.koin.dsl.module

internal val dataModuleDI = module {
    single { NetworkClient(enableLogging = true) }
    single { DBSourceImpl(get()) as DBSource }
}
