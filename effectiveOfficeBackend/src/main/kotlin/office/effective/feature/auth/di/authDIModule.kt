package office.effective.feature.auth.di

import office.effective.feature.auth.ITokenVerifier
import office.effective.feature.auth.TokenVerifier
import office.effective.feature.auth.converters.IntegrationModelEntityConverter
import office.effective.feature.auth.converters.UserDTOModelConverter
import office.effective.feature.auth.converters.UserModelEntityConverter
import office.effective.feature.auth.repository.UserRepository
import office.effective.feature.auth.service.IUserService
import office.effective.feature.auth.service.UserService
import org.koin.dsl.module

val authDIModule = module(createdAtStart = true) {
    single<ITokenVerifier> { TokenVerifier() }
    single<IUserService> { UserService() }
    single<IntegrationModelEntityConverter> { IntegrationModelEntityConverter() }
    single<UserModelEntityConverter> { UserModelEntityConverter() }
    single<UserDTOModelConverter> { UserDTOModelConverter() }
    single<UserRepository> { UserRepository() }
}