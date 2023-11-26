package office.effective.features.simpleAuth.di

import office.effective.features.simpleAuth.service.ApiKeyVerifier
import office.effective.features.simpleAuth.service.ITokenAuthorizer
import office.effective.features.simpleAuth.service.TokenVerifier
import office.effective.features.simpleAuth.repository.AuthRepository
import office.effective.features.simpleAuth.service.AuthorizationPipeline
import office.effective.features.simpleAuth.service.RequestVerifier
import org.koin.dsl.module

val authDiModule = module(createdAtStart = true) {
    single { AuthRepository(get()) }
    single { AuthorizationPipeline(makeList()) }
}

/**
 * Describes list of authentication handlers
 * */
fun makeList(): List<ITokenAuthorizer> {
    val list: MutableList<ITokenAuthorizer> = mutableListOf()
    list.add(TokenVerifier())
    list.add(RequestVerifier())
    list.add(ApiKeyVerifier())
    return list
}