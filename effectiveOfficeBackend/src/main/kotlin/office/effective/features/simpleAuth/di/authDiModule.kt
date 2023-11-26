package office.effective.features.simpleAuth.di

import office.effective.features.simpleAuth.repository.AuthRepository
import office.effective.features.simpleAuth.service.*
import org.koin.dsl.module

val authDiModule = module(createdAtStart = true) {
    single { AuthRepository(get()) }
    single { AuthorizationPipeline(null)
        .addAuthorizer(TokenAuthorizer(get()))
        .addAuthorizer(RequestAuthorizer(get()))
        .addAuthorizer(ApiKeyAuthorizer())
    }
    single { TokenExtractor() }

}

/**
 * Describes list of authentication handlers
 * */
fun makeList(): List<ITokenAuthorizer> {
    val list: MutableList<ITokenAuthorizer> = mutableListOf()
    list.add(TokenAuthorizer())
    list.add(RequestAuthorizer())
    list.add(ApiKeyAuthorizer())
    return list
}