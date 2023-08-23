package office.effective.plugins

import com.google.api.services.calendar.Calendar
import com.google.api.services.calendar.model.Channel
import io.ktor.server.application.*
import office.effective.features.calendar.repository.CalendarRepository
import org.koin.core.context.GlobalContext

/**
 * Subscribe to Google Calendar push notifications. Should be called after DI configuration
 *
 * @author Daniil Zavyalov
 */
fun Application.configureCalendarNotifications() {

    val calendar: Calendar = GlobalContext.get().get()
    val appAddress: String = System.getenv("DATABASE_URL")
    val calendarRepository: CalendarRepository = GlobalContext.get().get()

    val channel = Channel().apply {
        id = "6a120681-913e-4749-9b9f-10948d868d9f"
        type = "web_hook"
        address = appAddress
    }
    calendarRepository.findAllCalendarsId().forEach {
            id -> calendar.events().watch(id, channel).execute()
    }
}