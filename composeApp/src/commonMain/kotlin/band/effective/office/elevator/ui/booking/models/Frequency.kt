package band.effective.office.elevator.ui.booking.models

class Frequency(private val days: List<Pair<String, Int>>) {

//    private fun replaceNumbersWithWeekdays(): List<String> {
//        val weekdays = listOf("Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Вс")
//        return days.map { weekdays[it.second - 1] }
//    }

    override fun toString(): String {
//        val weekdays = replaceNumbersWithWeekdays()
        val distinctWeekdays = days.distinct()
        return distinctWeekdays.joinToString(", ") { it.first }
    }
}