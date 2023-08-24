package office.effective.features.booking.config

import office.effective.features.booking.converters.BookingFacadeConverter
import office.effective.features.booking.converters.BookingRepositoryConverter
import office.effective.features.booking.facade.BookingFacade
import office.effective.features.booking.repository.BookingCalendarRepository
import office.effective.features.booking.repository.BookingRepository
import office.effective.features.booking.repository.IBookingRepository
import office.effective.features.booking.service.BookingService
import org.koin.dsl.module

val bookingDiModule = module(createdAtStart = true) {
    single { BookingRepositoryConverter(get(), get(), get(),  get()) }
    single<IBookingRepository> { BookingCalendarRepository(get(), get(), get(), get()) }
    single { BookingService(get(), get(), get()) }
    single { BookingFacadeConverter(get(), get()) }
    single { BookingFacade(get(), get(), get(), get()) }
}
