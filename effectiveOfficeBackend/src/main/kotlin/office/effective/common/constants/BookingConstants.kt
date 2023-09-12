package office.effective.common.constants

/**
 * Constants for booking
 */
object BookingConstants {
    /**
     * Minimum booking start time.
     * Bookings that started earlier should be filtered out in requests.
     */
    val MIN_SEARCH_START_TIME = office.effective.config.propertyOrNull("calendar.minTime")?.getString()?.toLong()
        ?: throw Exception("Config file does not contain minimum time")
}
