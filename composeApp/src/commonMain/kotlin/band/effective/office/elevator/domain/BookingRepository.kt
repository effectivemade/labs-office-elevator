package band.effective.office.elevator.domain

import band.effective.office.elevator.domain.models.BookingIfo
import band.effective.office.elevator.ui.content.ContentComponent
import kotlinx.coroutines.flow.StateFlow
import kotlinx.datetime.LocalDate

interface BookingRepository {
    suspend fun changeBooking(bookId: Long, bookingIfo: BookingIfo)

    suspend fun createBook(bookingIfo: BookingIfo)

    suspend fun getBookingsForUser(): StateFlow<List<BookingIfo>>

    suspend fun getBookingsByDate(date: LocalDate): StateFlow<List<BookingIfo>>
}