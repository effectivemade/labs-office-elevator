package band.effective.office.elevator.domain.repository

import band.effective.office.elevator.domain.models.BookingInfo
import band.effective.office.elevator.domain.models.CreatingBookModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.datetime.LocalDate

interface BookingRepository {
    suspend fun changeBooking(bookingInfo: BookingInfo)

    suspend fun createBook(bookingInfo: CreatingBookModel)

    suspend fun getBookingsForUser(): StateFlow<List<BookingInfo>>

    suspend fun getBookingsByDate(date: LocalDate): StateFlow<List<BookingInfo>>
}