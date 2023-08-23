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
    val appAddress: String = System.getenv("APPLICATION_URL")
    val calendarRepository: CalendarRepository = GlobalContext.get().get()
    calendarRepository.findAllCalendarsId().forEach { calendar_id ->
        val channel = Channel().apply {
            id = "4a074113-b435-4a07-a620-bfc56e331de9"
            type = "web_hook"
            address = "$appAddress/notifications"
        }
        try {
            calendar.events().watch(calendar_id, channel).execute()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}