package band.effective.office.elevator.di

import band.effective.office.elevator.domain.GoogleSignIn
import band.effective.office.elevator.domain.GoogleSignInImpl
import dev.icerock.moko.permissions.PermissionsController
import org.koin.dsl.module

val androidModuleDI = module {
    single<GoogleSignIn> { GoogleSignInImpl() }
}

val permissionModule = module {
    single { PermissionsController(applicationContext = get()) }
}

