package band.effective.office.elevator.domain.models

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

sealed class BookingPeriod(val durationPeriod: Int) {
    data class Month(val monthPeriod: Int) : BookingPeriod(monthPeriod)

    data class Year(val yearPeriod: Int) : BookingPeriod(yearPeriod)

    data class EveryWorkDay(val workPeriod: Int) : BookingPeriod(workPeriod)

    data class Week(val weekPeriod: Int, val selectedDayOfWeek: List<DayOfWeek>) :
        BookingPeriod(weekPeriod)

    object NoPeriod : BookingPeriod(0)

    object Another : BookingPeriod(-1)
}

sealed interface TypeEndPeriodBooking{
    object Never : TypeEndPeriodBooking

    data class DatePeriodEnd(val date: LocalDate) : TypeEndPeriodBooking

    data class CountRepeat(val count: Int) : TypeEndPeriodBooking
}
enum class DayOfWeek(val dayOfWeekNumber: Int) {
    Sunday(1),
    Monday(2),
    Tuesday(3),
    Wednesday(4),
    Thursday(5),
    Friday(6),
    Saturday(7),
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
        beginBooking = dateOfStart.toInstant(timeZone = TimeZone.currentSystemDefault()).epochSeconds,
        endBooking = dateOfEnd.toInstant(timeZone = TimeZone.currentSystemDefault()).epochSeconds,
        recurrence = recurrence
    )