package band.effective.office.elevator.domain.di

import band.effective.office.elevator.data.ProfileRepositoryImpl
import band.effective.office.elevator.domain.ProfileRepository
import band.effective.office.elevator.domain.usecase.GetUserByIdUseCase
import org.koin.dsl.module

internal val profileDomainModuleDI = module{
    single<ProfileRepository> { ProfileRepositoryImpl() }
    single<GetUserByIdUseCase> {GetUserByIdUseCase(get())}
}