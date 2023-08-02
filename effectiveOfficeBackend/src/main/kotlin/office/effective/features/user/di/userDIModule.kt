package office.effective.features.user.di

import office.effective.features.user.ITokenVerifier
import office.effective.features.user.TokenVerifier
import office.effective.features.user.converters.IntegrationModelEntityConverter
import office.effective.features.user.converters.UserDTOModelConverter
import office.effective.features.user.converters.UserModelEntityConverter
import office.effective.features.user.repository.UserRepository
import office.effective.features.user.service.IUserService
import office.effective.features.user.service.UserService
import org.koin.dsl.module

val userDIModule = module(createdAtStart = true) {
    single<ITokenVerifier> { TokenVerifier() }
    single<IUserService> { UserService(get(), get(), get()) }
    single<IntegrationModelEntityConverter> { IntegrationModelEntityConverter() }
    single<UserModelEntityConverter> { UserModelEntityConverter() }
    single<UserDTOModelConverter> { UserDTOModelConverter() }
    single<UserRepository> { UserRepository(get(), get()) }
}