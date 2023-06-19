package band.effective.office.elevator.di

import band.effective.office.elevator.OfficeElevatorConfig
import band.effective.office.elevator.domain.GoogleSignIn
import band.effective.office.elevator.domain.GoogleSignInImpl
import org.koin.dsl.module

internal val iosModuleDI = module {
    single<GoogleSignIn> {
        GoogleSignInImpl(
            gidClientId = OfficeElevatorConfig.iosClient,
            serverClientId = OfficeElevatorConfig.webClient
        )
    }
}
