package band.effective.office.elevator.domain.repository

import band.effective.office.elevator.domain.models.BookingInfoDomain
import band.effective.office.elevator.domain.models.CreatingBookModel
import band.effective.office.elevator.ui.employee.aboutEmployee.models.BookingsFilter
import kotlinx.coroutines.flow.StateFlow
import kotlinx.datetime.LocalDate

interface BookingRepository {
    suspend fun changeBooking(bookingInfoDomain: BookingInfoDomain)
    suspend fun deleteBooking(bookingInfoDomain: BookingInfoDomain)

    suspend fun createBook(bookingInfo: CreatingBookModel)

    suspend fun getBookingsForUser(ownerId:String, bookingsFilter: BookingsFilter): StateFlow<List<BookingInfoDomain>>

    suspend fun getBookingsByDate(date: LocalDate, ownerId:String, bookingsFilter: BookingsFilter): StateFlow<List<BookingInfoDomain>>
}