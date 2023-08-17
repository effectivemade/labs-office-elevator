package band.effective.office.elevator.ui.models

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import kotlinx.datetime.LocalDate

data class ReservedSeat(
    val bookingId: String,
    val ownerId: String,
    val seatName: String,
    val bookingDay: String,
    val bookingTime: String,
    val bookingDate: LocalDate
)