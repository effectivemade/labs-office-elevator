package band.effective.office.elevator.data.di

import band.effective.office.elevator.data.NetworkClient
import org.koin.dsl.module

internal val dataModuleDI = module {
    single { NetworkClient(enableLogging = true) }
}
