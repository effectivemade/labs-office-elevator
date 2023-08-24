package band.effective.office.elevator.domain.di

import band.effective.office.elevator.data.repository.AuthorizationRepositoryImpl
import band.effective.office.elevator.data.repository.BookingRepositoryImpl
import band.effective.office.elevator.data.repository.EmployeeRepositoryImpl
import band.effective.office.elevator.data.repository.OfficeElevatorRepositoryImpl
import band.effective.office.elevator.data.repository.WorkspaceRepositoryImpl
import band.effective.office.elevator.domain.repository.BookingRepository
import band.effective.office.elevator.domain.repository.OfficeElevatorRepository
import band.effective.office.elevator.domain.useCase.EmployeeUseCase
import band.effective.office.elevator.domain.useCase.ElevatorCallUseCase
import band.effective.office.elevator.domain.useCase.GetBookingsUseCase
import band.effective.office.elevator.domain.entity.AuthorizationUseCase
import band.effective.office.elevator.domain.entity.BookingInteractor
import band.effective.office.elevator.domain.repository.AuthorizationRepository
import band.effective.office.elevator.domain.repository.EmployeeRepository
import band.effective.office.elevator.domain.repository.WorkspaceRepository
import band.effective.office.elevator.domain.useCase.AboutEmployeeInteractor
import band.effective.office.elevator.domain.useCase.ChangeBookingUseCase
import band.effective.office.elevator.domain.useCase.CreateBookingUseCase
import band.effective.office.elevator.domain.useCase.WorkspacesUseCase
import org.koin.dsl.module

internal val domainModuleDI = module {
    single<OfficeElevatorRepository> { OfficeElevatorRepositoryImpl(get(), get()) }

    single<EmployeeRepository> { EmployeeRepositoryImpl(api = get()) }
    single { EmployeeUseCase(repository = get()) }
    single { AboutEmployeeInteractor(bookingRepository = get(), employeeRepository = get()) }

    single { GetBookingsUseCase(get()) }
    single { ElevatorCallUseCase(get()) }
    single<BookingRepository> { BookingRepositoryImpl(api = get(), profileRepository = get()) }
    single {
        AuthorizationUseCase(authorizationRepository = get())
    }
    single {
        BookingInteractor(
            getBookingsUseCase = GetBookingsUseCase(repository = get()),
            changeBookingUseCase = ChangeBookingUseCase(repository = get()),
            createBookingUseCase = CreateBookingUseCase(repository = get()),
            workspaceUseCase = WorkspacesUseCase(repository = get()),
            repository = get()
        )
    }

    single<AuthorizationRepository> {
        AuthorizationRepositoryImpl(
            api = get(),
            bdSource = get()
        )
    }
    single<WorkspaceRepository> { WorkspaceRepositoryImpl(api = get()) }
}
