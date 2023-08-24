package band.effective.office.elevator.domain.repository

import band.effective.office.elevator.domain.models.BookingInfo
import band.effective.office.elevator.domain.models.BookingPeriod
import band.effective.office.elevator.domain.models.CreatingBookModel
import band.effective.office.elevator.domain.models.ErrorWithData
import band.effective.office.elevator.domain.models.TypeEndPeriodBooking
import band.effective.office.elevator.ui.employee.aboutEmployee.models.BookingsFilter
import band.effective.office.network.model.Either
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate

interface BookingRepository {
    suspend fun changeBooking(
        bookingInfo: BookingInfo,
        bookingPeriod: BookingPeriod? = null,
        typeEndPeriod: TypeEndPeriodBooking? = null
    )
    suspend fun deleteBooking(bookingInfo: BookingInfo)

    suspend fun deleteBooking(book: String)
    suspend fun createBook(creatingBookModel: CreatingBookModel)

    suspend fun getBookingsForUser(
        ownerId: String,
        bookingsFilter: BookingsFilter
    ): Flow<Either<ErrorWithData<List<BookingInfo>>, List<BookingInfo>>>

    suspend fun getBookingsByDate(
        date: LocalDate,
        bookingsFilter: BookingsFilter
    ): Flow<Either<ErrorWithData<List<BookingInfo>>, List<BookingInfo>>>
}