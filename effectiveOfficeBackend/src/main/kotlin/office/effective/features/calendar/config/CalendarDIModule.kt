package office.effective.features.calendar.config

import office.effective.features.calendar.repository.CalendarRepository
import org.koin.dsl.module

val calendarDiModule = module(createdAtStart = true) {
    single { CalendarRepository(get(), get(), get()) }
}