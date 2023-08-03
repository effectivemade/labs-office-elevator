package office.effective.features.booking.config

import office.effective.features.booking.converters.BookingRepositoryConverter
import office.effective.features.booking.facade.BookingFacade
import office.effective.features.booking.repository.BookingRepository
import office.effective.features.booking.service.BookingService
import org.koin.dsl.module

val bookingDiModule = module(createdAtStart = true) {
    single { BookingRepositoryConverter(get(), get(), get()) }
    single { BookingRepository(get(), get()) }
    single { BookingService(get(), get(), get()) }
    single { BookingFacade(get(), get(), get(), get()) }
}
