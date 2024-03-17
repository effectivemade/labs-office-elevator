package office.effective.features.metrics.di

import office.effective.common.constants.BookingConstants
import office.effective.features.booking.service.BookingService
import office.effective.features.metrics.service.MetricsService
import org.koin.dsl.module

val metricsDiModule = module(createdAtStart = true) {
    single {
        MetricsService(
            get(),
            get(),
            BookingService(get(), get(), get(), get()),
            BookingConstants
        )
    }
}