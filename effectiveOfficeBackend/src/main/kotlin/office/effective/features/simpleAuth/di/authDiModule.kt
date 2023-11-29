package office.effective.features.simpleAuth.di

import office.effective.features.simpleAuth.repository.AuthRepository
import office.effective.features.simpleAuth.service.*
import org.koin.dsl.module

val authDiModule = module(createdAtStart = true) {
    single { AuthRepository(get()) }
    single {
        AuthorizationPipeline()
            .addAuthorizer(PermitAuthorizer(listOf("/swagger/", "/notifications" )))
            .addAuthorizer(TokenAuthorizer(get()))
            .addAuthorizer(RequestAuthorizer(get()))
            .addAuthorizer(ApiKeyAuthorizer())
    }
    single { TokenExtractor() }

}
