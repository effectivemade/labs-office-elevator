package band.effective.office.elevator.ui.booking.models

import band.effective.office.elevator.domain.models.DayOfWeek

class Frequency(private val days: List<Pair<String, Int>>, private val researchEnd: Triple<Pair <String, String>, String, String>) {

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

    fun getResearchEnd(): Triple<Pair <String, String>, String, String>{

        return researchEnd
    }

    override fun toString(): String {
        val distinctWeekdays = days.distinct()
        return distinctWeekdays.joinToString(", ") { it.first }
    }
}