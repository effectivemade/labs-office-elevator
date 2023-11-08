package office.effective.common.constants

import office.effective.config

/**
 * Constants for booking
 */
object BookingConstants {
    /**
     * Minimum booking start time.
     * Bookings that started earlier should be filtered out in requests.
     */
    val MIN_SEARCH_START_TIME = config.propertyOrNull("calendar.minTime")?.getString()?.toLong()
        ?: throw Exception("Config file does not contain minimum time")
    val DEFAULT_CALENDAR: String = config.propertyOrNull("calendar.defaultCalendar")?.getString()
        ?: throw Exception("Config file does not contain Google default calendar id")
    val WORKSPACE_CALENDAR: String = config.propertyOrNull("calendar.workspaceCalendar")?.getString()
        ?: throw Exception("Config file does not contain workspace Google calendar id")
}
