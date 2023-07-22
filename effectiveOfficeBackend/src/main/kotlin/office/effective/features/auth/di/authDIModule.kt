package office.effective.features.auth.di

import office.effective.features.auth.ITokenVerifier
import office.effective.features.auth.TokenVerifier
import office.effective.features.auth.converters.IntegrationModelEntityConverter
import office.effective.features.auth.converters.UserDTOModelConverter
import office.effective.features.auth.converters.UserModelEntityConverter
import office.effective.features.auth.repository.UserRepository
import office.effective.features.auth.service.IUserService
import office.effective.features.auth.service.UserService
import org.koin.dsl.module

val authDIModule = module(createdAtStart = true) {
    single<ITokenVerifier> { TokenVerifier() }
    single<IUserService> { UserService() }
    single<IntegrationModelEntityConverter> { IntegrationModelEntityConverter() }
    single<UserModelEntityConverter> { UserModelEntityConverter() }
    single<UserDTOModelConverter> { UserDTOModelConverter() }
    single<UserRepository> { UserRepository() }
}