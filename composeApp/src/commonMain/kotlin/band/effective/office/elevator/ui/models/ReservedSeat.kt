package band.effective.office.elevator.ui.models

import kotlinx.datetime.LocalDate

data class ReservedSeat(
    val bookingId: String,
    val ownerId: String,
    val seatName: String,
    val bookingDay: String,
    val bookingTime: String,
    val bookingDate: LocalDate
)