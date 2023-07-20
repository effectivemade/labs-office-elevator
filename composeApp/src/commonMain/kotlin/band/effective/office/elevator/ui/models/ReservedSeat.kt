package band.effective.office.elevator.ui.models

import kotlinx.datetime.LocalDate

data class ReservedSeat(
    val seatName: String,
    val bookingDay: String,
    val bookingTime: String,
    val bookingDate: LocalDate
)