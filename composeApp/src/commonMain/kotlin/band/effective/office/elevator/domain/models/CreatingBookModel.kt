package band.effective.office.elevator.domain.models

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

data class CreatingBookModel(
    val workSpaceId: String,
    val dateOfStart: LocalDateTime,
    val dateOfEnd: LocalDateTime,
    val bookingPeriod: BookingPeriod,
    val typeOfEndPeriod: TypeEndPeriodBooking
)

sealed class BookingPeriod(val durationPeriod: Int) {
    data class Month(val monthPeriod: Int) : BookingPeriod(monthPeriod)

    data class Year(val yearPeriod: Int) : BookingPeriod(yearPeriod)

    data class EveryWorkDay(val workPeriod: Int) : BookingPeriod(workPeriod)

    data class Week(val weekPeriod: Int, val selectedDayOfWeek: List<DayOfWeek>) :
        BookingPeriod(weekPeriod)

    object NoPeriod : BookingPeriod(0)
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
