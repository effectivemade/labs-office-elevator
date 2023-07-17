package office.effective.feature.auth.di

import office.effective.feature.auth.ITokenVerifier
import office.effective.feature.auth.TokenVerifier
import org.koin.dsl.module

val authDIModule = module(createdAtStart = true) {
    single<ITokenVerifier> { TokenVerifier() }
}