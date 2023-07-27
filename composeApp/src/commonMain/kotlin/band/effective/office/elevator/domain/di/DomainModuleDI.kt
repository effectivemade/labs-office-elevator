package band.effective.office.elevator.domain.di

import band.effective.office.elevator.data.OfficeElevatorRepositoryImpl
import band.effective.office.elevator.domain.OfficeElevatorRepository
import band.effective.office.elevator.data.ProfileRepositoryImpl
import band.effective.office.elevator.domain.ProfileRepository
import org.koin.dsl.module

internal val domainModuleDI = module {
    single<OfficeElevatorRepository> { OfficeElevatorRepositoryImpl(get(), get()) }
    single<ProfileRepository> { ProfileRepositoryImpl() }
}
