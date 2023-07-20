package band.effective.office.elevator.di

import band.effective.office.elevator.OfficeElevatorConfig
import band.effective.office.elevator.domain.GoogleSignIn
import band.effective.office.elevator.domain.GoogleSignInImpl
import dev.icerock.moko.geo.LocationTracker
import dev.icerock.moko.permissions.ios.PermissionsController
import org.koin.dsl.module

internal val iosModuleDI = module {
    single<GoogleSignIn> {
        GoogleSignInImpl(
            gidClientId = OfficeElevatorConfig.iosClient,
            serverClientId = OfficeElevatorConfig.webClient
        )
    }
    single {
        LocationTracker(
            permissionsController = PermissionsController(),
            accuracy = kCLLocationAccuracyBest
        )
    }
}
