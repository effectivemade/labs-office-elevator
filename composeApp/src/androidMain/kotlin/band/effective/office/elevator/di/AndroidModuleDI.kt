package band.effective.office.elevator.di

import band.effective.office.elevator.domain.GoogleSignIn
import band.effective.office.elevator.domain.GoogleSignInImpl
import org.koin.dsl.module

val androidModuleDI = module {
    single<GoogleSignIn> { GoogleSignInImpl() }
}
