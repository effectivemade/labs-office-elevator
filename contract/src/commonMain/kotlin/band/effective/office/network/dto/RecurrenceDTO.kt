package band.effective.office.network.dto

import kotlinx.serialization.Serializable

//TODO (Atem Gruzdev) fix this, then will new release
@Serializable
data class RecurrenceDTO(
    val interval: Int? = null, // period bookings
    val freq: String, // DAILY, WEEKLY, MONTHLY, YEARLY
    val count: Int? = null, // {count : 2, byDay[2,3,5], freq: Weekly} // count * byDay.length()
    val until: Long? = null, // end date
    val byDay: List<Int> = listOf(),
    val byMonth: List<Int> = listOf(),
    val byYearDay: List<Int> = listOf(),
    val byHour: List<Int> = listOf()
)
