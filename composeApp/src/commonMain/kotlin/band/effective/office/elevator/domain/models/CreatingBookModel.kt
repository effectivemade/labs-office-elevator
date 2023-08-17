package band.effective.office.elevator.domain.models

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

data class CreatingBookModel(
    val workSpaceId: String,
    val dateOfStart: LocalDateTime,
    val dateOfEnd: LocalDateTime,
    val bookingPeriodUI: BookingPeriodUI,
    val typeOfEndPeriod: TypeEndPeriodBooking
)

sealed class BookingPeriodUI(val durationPeriod: Int) {
    data class Month(val monthPeriod: Int) : BookingPeriodUI(monthPeriod)

    data class Year(val yearPeriod: Int) : BookingPeriodUI(yearPeriod)

    data class EveryWorkDay(val workPeriod: Int) : BookingPeriodUI(workPeriod)

    data class Week(val weekPeriod: Int, val selectedDayOfWeek: List<DayOfWeek>) :
        BookingPeriodUI(weekPeriod)

    object NoPeriod : BookingPeriodUI(0)
}

sealed interface TypeEndPeriodBooking{
    object Never : TypeEndPeriodBooking

    data class DatePeriodEnd(val date: LocalDate) : TypeEndPeriodBooking

    data class CountRepeat(val count: Int) : TypeEndPeriodBooking
}
enum class DayOfWeek {
    Monday,
    Tuesday,
    Wednesday,
    Thursday,
    Friday,
    Saturday
}
