package office.effective.features.simpleAuth.di

import office.effective.features.simpleAuth.repository.AuthRepository
import org.koin.dsl.module

val authDiModule = module(createdAtStart = true) {
    single { AuthRepository(get()) }
}