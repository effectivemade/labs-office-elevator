package office.effective.plugins

import com.google.api.services.calendar.Calendar
import com.google.api.services.calendar.model.Channel
import io.ktor.server.application.*
import office.effective.config
import office.effective.features.calendar.repository.CalendarRepository
import org.koin.core.context.GlobalContext
import java.util.*

/**
 * Subscribe to Google Calendar push notifications. Should be called after DI configuration
 *
 * @author Daniil Zavyalov
 */
fun Application.configureCalendarNotifications() {
    val defaultCalendar = config.propertyOrNull("auth.app.defaultAppEmail")?.getString()
        ?: throw Exception("Config file does not contain default gmail value")

    val calendar: Calendar = GlobalContext.get().get()
    val appAddress: String = System.getenv("APPLICATION_URL")
    val calendarRepository: CalendarRepository = GlobalContext.get().get()
    val calendarIds = calendarRepository.findAllCalendarsId().toMutableList()
    calendarIds.add(defaultCalendar)
    calendarIds.forEach { calendar_id ->
        val channel = Channel().apply {
            id = UUID.randomUUID().toString()
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