package band.effective.office.elevator.domain.di

import band.effective.office.elevator.data.repository.EmployeeRepositoryImpl
import band.effective.office.elevator.data.repository.MockBookingRepositoryImpl
import band.effective.office.elevator.data.repository.OfficeElevatorRepositoryImpl
import band.effective.office.elevator.data.repository.UserProfileRepositoryImpl
import band.effective.office.elevator.domain.repository.BookingRepository
import band.effective.office.elevator.domain.repository.OfficeElevatorRepository
import band.effective.office.elevator.domain.entity.AuthorizationEntity
import band.effective.office.elevator.domain.entity.BookingInteractor
import band.effective.office.elevator.domain.repository.EmployeeRepository
import band.effective.office.elevator.domain.repository.UserProfileRepository
import band.effective.office.elevator.domain.useCase.AboutEmployeeUseCase
import band.effective.office.elevator.domain.useCase.ChangeBookingUseCase
import band.effective.office.elevator.domain.useCase.CreateBookingUseCase
import band.effective.office.elevator.domain.useCase.ElevatorCallUseCase
import band.effective.office.elevator.domain.useCase.EmployeeUseCase
import band.effective.office.elevator.domain.useCase.GetBookingsUseCase
import band.effective.office.elevator.domain.useCase.GetUserUseCase
import band.effective.office.elevator.domain.useCase.PushUserDataUseCase
import org.koin.dsl.module

internal val domainModuleDI = module {
    single<OfficeElevatorRepository> { OfficeElevatorRepositoryImpl(get(), get()) }

    single<EmployeeRepository> { EmployeeRepositoryImpl() }
    factory<EmployeeUseCase> { EmployeeUseCase(repository = get()) }
    factory<AboutEmployeeUseCase> { AboutEmployeeUseCase(repository = get()) }

    single { GetBookingsUseCase(get()) }
    single { ElevatorCallUseCase(get()) }
    single<BookingRepository> { MockBookingRepositoryImpl() }
    single<UserProfileRepository> { UserProfileRepositoryImpl() }
    single<AuthorizationEntity> {
        AuthorizationEntity(
            GetUserUseCase(get()),
            PushUserDataUseCase(get())
        )
    }
    single<BookingInteractor> {
        BookingInteractor(
            getBookingsUseCase = GetBookingsUseCase(repository = get()),
            changeBookingUseCase = ChangeBookingUseCase(repository = get()),
            createBookingUseCase = CreateBookingUseCase(repository = get())
        )
    }

}
