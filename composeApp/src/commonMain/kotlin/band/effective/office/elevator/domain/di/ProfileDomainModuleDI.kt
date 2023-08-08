package band.effective.office.elevator.domain.di

import band.effective.office.elevator.data.repository.ProfileRepositoryImpl
import band.effective.office.elevator.data.repository.UserRepositoryImpl
import band.effective.office.elevator.domain.repository.ProfileRepository
import band.effective.office.elevator.domain.repository.UserRepository
import band.effective.office.elevator.domain.useCase.GetLastUserIdUseCase
import band.effective.office.elevator.domain.useCase.GetUserByIdUseCase
import band.effective.office.elevator.domain.useCase.UpdateUserUseCase
import org.koin.dsl.module

internal val profileDomainModuleDI = module{
    single<ProfileRepository> { ProfileRepositoryImpl() }
    single<GetUserByIdUseCase> {GetUserByIdUseCase(get())}
    single<UpdateUserUseCase> {UpdateUserUseCase(get())}
    single<UserRepository> { UserRepositoryImpl() }
    single { GetLastUserIdUseCase(get()) }
}