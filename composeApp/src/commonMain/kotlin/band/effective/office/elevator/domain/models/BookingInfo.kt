package band.effective.office.elevator.domain.models

import band.effective.office.elevator.ui.models.ReservedSeat
import band.effective.office.elevator.utils.capitalizeFirstLetter
import band.effective.office.elevator.utils.localDateTimeToUnix
import band.effective.office.elevator.utils.unixToLocalDateTime
import band.effective.office.network.dto.BookingDTO
import band.effective.office.network.dto.RecurrenceDTO
import band.effective.office.network.dto.UserDTO
import band.effective.office.network.dto.WorkspaceDTO
import epicarchitect.calendar.compose.basis.localized
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime

data class BookingInfo(
    val id: String,
    val ownerId: String,
    val workSpaceId: String,
    val seatName: String,
    val dateOfStart: LocalDateTime,
    val dateOfEnd: LocalDateTime
)

fun BookingDTO.toDomainModel() =
    BookingInfo(
        id = id!!,
        ownerId = owner.id,
        workSpaceId = workspace.id,
        seatName = workspace.name,
        dateOfStart = unixToLocalDateTime(beginBooking),
        dateOfEnd = unixToLocalDateTime(endBooking),
    )

fun List<BookingDTO>.toDomainZone() = map { it.toDomainModel() }
fun emptyUserDTO(id: String): UserDTO =
    UserDTO(
        id = id,
        fullName = "",
        active = false,
        role = "",
        avatarUrl = "",
        integrations = null,
        ""
    )


fun emptyWorkSpaceDTO(id: String) =
    WorkspaceDTO(
        id = id,
        name = "",
        utilities = listOf(),
        zone = null,
        tag = "regular"
    )

fun BookingInfo.toDTOModel(userDTO: UserDTO, workspaceDTO: WorkspaceDTO, recurrence: RecurrenceDTO?) =
    BookingDTO(
        owner = userDTO,
        participants = listOf(userDTO),
        workspace = workspaceDTO,
        id = id,
        beginBooking = localDateTimeToUnix(dateOfStart)!!,
        endBooking = localDateTimeToUnix(dateOfEnd)!!,
        recurrence = recurrence
    )

fun BookingInfo.toUiModel() = ReservedSeat(
    ownerId = ownerId,
    bookingId = id,
    seatName = seatName,
    bookingDate = dateOfStart.date,
    bookingDay = convertDateTimeToUiDateString(dateOfStart),
    bookingTime = convertDateTimeToUiTimeString(
        startTime = dateOfStart.time,
        endTime = dateOfEnd.time
    )
)
fun List<BookingInfo>.toUIModel() = map { it.toUiModel() }

private fun convertDateTimeToUiDateString(dateOfStart: LocalDateTime) =
    "${capitalizeFirstLetter(dateOfStart.dayOfWeek.localized())}, ${dateOfStart.dayOfMonth} ${dateOfStart.month}"

private fun convertDateTimeToUiTimeString(
    startTime: LocalTime,
    endTime: LocalTime
) = "${startTime.hour}:${startTime.minute} - ${endTime.hour}:${endTime.minute}"
