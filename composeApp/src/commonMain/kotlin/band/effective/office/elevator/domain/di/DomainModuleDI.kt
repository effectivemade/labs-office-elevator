package band.effective.office.elevator.domain.di

import band.effective.office.elevator.data.repository.EmployeeRepositoryImpl
import band.effective.office.elevator.data.repository.MockBookingRepositoryImpl
import band.effective.office.elevator.data.repository.OfficeElevatorRepositoryImpl
import band.effective.office.elevator.data.repository.UserProfileRepositoryImpl
import band.effective.office.elevator.domain.repository.BookingRepository
import band.effective.office.elevator.domain.repository.OfficeElevatorRepository
import band.effective.office.elevator.domain.OfficeElevatorRepository
import band.effective.office.elevator.domain.useCase.EmployeeUseCase
import band.effective.office.elevator.domain.useCase.ElevatorCallUseCase
import band.effective.office.elevator.domain.useCase.GetBookingsUseCase
import band.effective.office.elevator.domain.entity.AuthorizationUseCase
import band.effective.office.elevator.domain.entity.BookingInteractor
import band.effective.office.elevator.domain.repository.EmployeeRepository
import band.effective.office.elevator.domain.useCase.AboutEmployeeInteractor
import band.effective.office.elevator.domain.useCase.ChangeBookingUseCase
import band.effective.office.elevator.domain.useCase.CreateBookingUseCase
import band.effective.office.elevator.domain.useCase.GetUserUseCase
import org.koin.dsl.module

internal val domainModuleDI = module {
    single<OfficeElevatorRepository> { OfficeElevatorRepositoryImpl(get(), get()) }

    single<EmployeeRepository> { EmployeeRepositoryImpl(api = get()) }
    single { EmployeeUseCase(repository = get()) }
    single { AboutEmployeeInteractor(bookingRepository = get(), employeeRepository = get()) }

    single { GetBookingsUseCase(get()) }
    single { ElevatorCallUseCase(get()) }
    single<BookingRepository> { MockBookingRepositoryImpl() }
    single {
        AuthorizationUseCase(authorizationRepository = get())
    }
    single {
        BookingInteractor(
            getBookingsUseCase = GetBookingsUseCase(repository = get()),
            changeBookingUseCase = ChangeBookingUseCase(repository = get()),
            createBookingUseCase = CreateBookingUseCase(repository = get())
        )
    }

}
