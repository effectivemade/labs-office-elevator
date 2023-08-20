package band.effective.office.elevator.domain.repository

import band.effective.office.elevator.domain.models.BookingInfo
import band.effective.office.elevator.domain.models.CreatingBookModel
import band.effective.office.elevator.ui.employee.aboutEmployee.models.BookingsFilter
import kotlinx.coroutines.flow.StateFlow
import kotlinx.datetime.LocalDate

interface BookingRepository {
    suspend fun changeBooking(bookingInfo: BookingInfo)
    suspend fun deleteBooking(bookingInfo: BookingInfo)

    suspend fun createBook(bookingInfo: CreatingBookModel)

    suspend fun getBookingsForUser(ownerId:String, bookingsFilter: BookingsFilter): StateFlow<List<BookingInfo>>

    suspend fun getBookingsByDate(date: LocalDate, ownerId:String, bookingsFilter: BookingsFilter): StateFlow<List<BookingInfo>>
}