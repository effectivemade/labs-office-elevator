package band.effective.office.elevator.domain.di

import band.effective.office.elevator.data.OfficeElevatorRepositoryImpl
import band.effective.office.elevator.data.repository.UserProfileRepositoryImpl
import band.effective.office.elevator.domain.OfficeElevatorRepository
import band.effective.office.elevator.domain.repository.UserProfileRepository
import band.effective.office.elevator.domain.usecase.phone_authorization.GetUserUseCase
import org.koin.dsl.module

internal val domainModuleDI = module {
    single<OfficeElevatorRepository> { OfficeElevatorRepositoryImpl(get(), get()) }
    single<UserProfileRepository> { UserProfileRepositoryImpl() }
    single<GetUserUseCase> { GetUserUseCase(get()) }
}
