package band.effective.office.tablet.di

import band.effective.office.tablet.domain.CurrentEventController
import band.effective.office.tablet.domain.CurrentEventControllerServerImpl
import band.effective.office.tablet.domain.MockController
import band.effective.office.tablet.domain.useCase.BookingUseCase
import band.effective.office.tablet.domain.useCase.OrganizersInfoUseCase
import band.effective.office.tablet.domain.useCase.RoomInfoUseCase
import band.effective.office.tablet.network.api.WorkApi
import band.effective.office.tablet.network.repository.BookingRepository
import band.effective.office.tablet.network.repository.CancelRepository
import band.effective.office.tablet.network.repository.OrganizerRepository
import band.effective.office.tablet.network.repository.RoomRepository
import band.effective.office.tablet.network.repository.ServerUpdateRepository
import band.effective.office.tablet.network.repository.impl.BookingRepositoryImpl
import band.effective.office.tablet.network.repository.impl.CancelRepositoryImpl
import band.effective.office.tablet.network.repository.impl.OrganizerRepositoryImpl
import band.effective.office.tablet.network.repository.impl.RoomRepositoryImpl
import band.effective.office.tablet.network.repository.impl.ServerUpdateRepositoryImpl
import org.koin.dsl.module

val domainModule = module {
    single<RoomRepository> { RoomRepositoryImpl(get()) }
    single<OrganizerRepository> { OrganizerRepositoryImpl(get()) }
    single<CancelRepository> { CancelRepositoryImpl(get()) }
    single<BookingRepository> { BookingRepositoryImpl(get()) }
    single<ServerUpdateRepository> { ServerUpdateRepositoryImpl(get()) }

    single<RoomInfoUseCase> { RoomInfoUseCase(get()) }
    single<OrganizersInfoUseCase> { OrganizersInfoUseCase(get()) }
    single<BookingUseCase> { BookingUseCase(get()) }
    single<CurrentEventController> { CurrentEventControllerServerImpl(get(), get(), get()) }
}