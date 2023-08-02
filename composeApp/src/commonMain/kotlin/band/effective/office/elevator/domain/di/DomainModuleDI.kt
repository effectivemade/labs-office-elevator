package band.effective.office.elevator.domain.di

import band.effective.office.elevator.data.MockBookingRepositoryImpl
import band.effective.office.elevator.data.OfficeElevatorRepositoryImpl
import band.effective.office.elevator.domain.BookingRepository
import band.effective.office.elevator.domain.OfficeElevatorRepository
import band.effective.office.elevator.domain.useCase.ElevatorCallUseCase
import band.effective.office.elevator.domain.useCase.GetBookingsUseCase
import org.koin.dsl.module

internal val domainModuleDI = module {
    single<OfficeElevatorRepository> { OfficeElevatorRepositoryImpl(get(), get()) }
    single { GetBookingsUseCase(get()) }
    single { ElevatorCallUseCase(get()) }
    single<BookingRepository> { MockBookingRepositoryImpl() }
}
