package band.effective.office.elevator.domain.useCase

import band.effective.office.elevator.domain.BookingRepository
import band.effective.office.elevator.domain.models.toUIModel
import band.effective.office.elevator.ui.models.ReservedSeat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.datetime.LocalDate

class GetBookingsUseCase(
    private val repository: BookingRepository
) {
    suspend fun getBookingsForUser(
        coroutineScope: CoroutineScope
    ): StateFlow<List<ReservedSeat>> =
        repository.getBookingsForUser()
            .map { bookingsList -> bookingsList.toUIModel() }
            .stateIn(coroutineScope)

    suspend fun getBookingsByDate(
        date: LocalDate,
        coroutineScope: CoroutineScope
    ): StateFlow<List<ReservedSeat>> =
        repository.getBookingsByDate(date = date)
            .map { bookingsList -> bookingsList.toUIModel() }
            .stateIn(coroutineScope)
}