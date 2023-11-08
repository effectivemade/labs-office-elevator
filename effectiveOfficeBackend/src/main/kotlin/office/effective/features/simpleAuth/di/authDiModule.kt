package office.effective.features.simpleAuth.di

import office.effective.features.simpleAuth.ApiKeyVerifier
import office.effective.features.simpleAuth.ITokenVerifier
import office.effective.features.simpleAuth.TokenVerifier
import office.effective.features.simpleAuth.repository.AuthRepository
import office.effective.features.simpleAuth.service.AuthenticationPipeline
import office.effective.features.simpleAuth.service.RequestVerifier
import org.koin.dsl.module

val authDiModule = module(createdAtStart = true) {
    single { AuthRepository(get()) }
    single { AuthenticationPipeline(makeList()) }
}

/**
 * Describes list of authentication handlers
 * */
fun makeList(): List<ITokenVerifier> {
    val list: MutableList<ITokenVerifier> = mutableListOf()
    list.add(TokenVerifier())
    list.add(RequestVerifier())
    list.add(ApiKeyVerifier())
    return list
}