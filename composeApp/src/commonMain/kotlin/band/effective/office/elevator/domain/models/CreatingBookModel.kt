package band.effective.office.elevator.domain.models

import band.effective.office.elevator.utils.localDateTimeToUnix
import band.effective.office.network.dto.BookingDTO
import band.effective.office.network.dto.RecurrenceDTO
import band.effective.office.network.dto.UserDTO
import band.effective.office.network.dto.WorkspaceDTO
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant

data class CreatingBookModel(
    val workSpaceId: String,
    val dateOfStart: LocalDateTime,
    val dateOfEnd: LocalDateTime,
    val bookingPeriod: BookingPeriod,
    val typeOfEndPeriod: TypeEndPeriodBooking
)

sealed class BookingPeriod(val durationPeriod: Int?) {
    data class Month(val monthPeriod: Int) : BookingPeriod(monthPeriod)

    data class Year(val yearPeriod: Int) : BookingPeriod(yearPeriod)

    data class EveryWorkDay(val workPeriod: Int) : BookingPeriod(workPeriod)

    data class Week(val weekPeriod: Int, val selectedDayOfWeek: List<DayOfWeek>) :
        BookingPeriod(weekPeriod)

    object NoPeriod : BookingPeriod(null)

    object Another : BookingPeriod(null)

    object Day : BookingPeriod (null)
}

sealed interface TypeEndPeriodBooking{
    object Never : TypeEndPeriodBooking

    data class DatePeriodEnd(val date: LocalDate) : TypeEndPeriodBooking

    data class CountRepeat(val count: Int) : TypeEndPeriodBooking
}

//TDOD (Artem Gruzdev) Use WeekDay kotlinx.datetime.DayOfWeek
enum class DayOfWeek(val dayOfWeekNumber: Int) {
    Sunday(1),
    Monday(2),
    Tuesday(3),
    Wednesday(4),
    Thursday(5),
    Friday(6),
    Saturday(7),
}

fun DayOfWeek.dayOfWeekToString() =
    when(this) {
        DayOfWeek.Sunday -> "ВС"
        DayOfWeek.Monday -> "ПН"
        DayOfWeek.Tuesday -> "ВТ"
        DayOfWeek.Wednesday -> "СР"
        DayOfWeek.Thursday -> "ЧТ"
        DayOfWeek.Friday -> "ПТ"
        DayOfWeek.Saturday -> "СБ"
    }

fun List<DayOfWeek>.listToString() : String {
    var str = ""
    forEachIndexed { index, it ->
        str += "${it.dayOfWeekToString()}"
        if (index != size - 1) str += ", "
    }
    return str
}


fun CreatingBookModel.toDTO(
    user: UserDTO,
    workspaceDTO: WorkspaceDTO,
    recurrence: RecurrenceDTO?
) =
    BookingDTO(
        owner = user,
        participants = listOf(user),
        workspace = workspaceDTO,
        id = "",
        beginBooking = localDateTimeToUnix(dateOfStart)!!,
        endBooking = localDateTimeToUnix(dateOfEnd)!!,
        recurrence = recurrence
    )