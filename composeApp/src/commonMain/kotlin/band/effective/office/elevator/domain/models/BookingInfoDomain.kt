package band.effective.office.elevator.domain.models

import band.effective.office.elevator.ui.models.ReservedSeat
import band.effective.office.elevator.utils.capitalizeFirstLetter
import epicarchitect.calendar.compose.basis.localized
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime

data class BookingInfoDomain(
    val id: String,
    val ownerId: String,
    val seatName: String,
    val dateOfStart: LocalDateTime,
    val dateOfEnd: LocalDateTime
)

fun BookingInfoDomain.toUiModel() = ReservedSeat(
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
fun List<BookingInfoDomain>.toUIModel() = map { it.toUiModel() }

private fun convertDateTimeToUiDateString(dateOfStart: LocalDateTime) =
    "${capitalizeFirstLetter(dateOfStart.dayOfWeek.localized())}, ${dateOfStart.dayOfMonth} ${dateOfStart.month}"

private fun convertDateTimeToUiTimeString(
    startTime: LocalTime,
    endTime: LocalTime
) = "${startTime.hour}:${startTime.minute} - ${endTime.hour}:${endTime.minute}"
