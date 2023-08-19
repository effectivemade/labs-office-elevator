package band.effective.office.elevator.ui.booking.models

import band.effective.office.elevator.domain.models.DayOfWeek

class Frequency(private val days: List<Pair<String, Int>>) {

    fun getDays(): List<DayOfWeek> {
        val list = listOf(
            DayOfWeek.Monday,
            DayOfWeek.Tuesday,
            DayOfWeek.Wednesday,
            DayOfWeek.Thursday,
            DayOfWeek.Friday,
            DayOfWeek.Saturday
        )

        return days.map { list[it.second] }
    }

    override fun toString(): String {
        val distinctWeekdays = days.distinct()
        return distinctWeekdays.joinToString(", ") { it.first }
    }
}