package band.effective.office.elevator.domain.di

import band.effective.office.elevator.data.OfficeElevatorRepositoryImpl
import band.effective.office.elevator.domain.OfficeElevatorRepository
import band.effective.office.elevator.ui.profile.data.ProfileRepositoryImpl
import band.effective.office.elevator.ui.profile.domain.ProfileRepository
import org.koin.dsl.module

internal val domainModuleDI = module {
    single<OfficeElevatorRepository> { OfficeElevatorRepositoryImpl(get(), get()) }
    single<ProfileRepository> { ProfileRepositoryImpl()}
}
