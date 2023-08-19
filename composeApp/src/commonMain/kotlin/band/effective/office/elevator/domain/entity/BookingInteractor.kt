package band.effective.office.elevator.domain.entity

import band.effective.office.elevator.domain.models.BookingInfoDomain
import band.effective.office.elevator.domain.models.CreatingBookModel
import band.effective.office.elevator.domain.useCase.ChangeBookingUseCase
import band.effective.office.elevator.domain.useCase.CreateBookingUseCase
import band.effective.office.elevator.domain.useCase.GetBookingsUseCase
import band.effective.office.elevator.ui.employee.aboutEmployee.models.BookingsFilter
import band.effective.office.elevator.ui.models.ReservedSeat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.datetime.LocalDate

class BookingInteractor(
    private val getBookingsUseCase: GetBookingsUseCase,
    private val changeBookingUseCase: ChangeBookingUseCase,
    private val createBookingUseCase: CreateBookingUseCase
) {
    suspend fun getForUser(ownerId:String, coroutineScope: CoroutineScope): StateFlow<List<ReservedSeat>> {
        val bookingsForUser: StateFlow<List<ReservedSeat>> =
            getBookingsUseCase.getBookingsForUser(
                ownerId = ownerId,
                coroutineScope = coroutineScope,
                bookingsFilter = BookingsFilter(meetRoom = true, workPlace = true)
            )
        return bookingsForUser
    }

    suspend fun getByDate(
        ownerId: String,
        date: LocalDate,
        coroutineScope: CoroutineScope
    ): StateFlow<List<ReservedSeat>> {
        val bookingsByDate: StateFlow<List<ReservedSeat>> =
            getBookingsUseCase.getBookingsByDate(
                ownerId = ownerId, date = date,
                coroutineScope = coroutineScope,
                bookingsFilter = BookingsFilter(meetRoom = true, workPlace = true)
            )
        return bookingsByDate
    }

    suspend fun change(coroutineScope: CoroutineScope, bookingInfo: BookingInfoDomain) {
        changeBookingUseCase.execute(
            coroutineScope = coroutineScope,
            bookingInfoDomain = bookingInfo
        )
    }

    suspend fun create(coroutineScope: CoroutineScope, creatingBookModel: CreatingBookModel) {
        createBookingUseCase.execute(
            coroutineScope = coroutineScope,
            creatingBookModel = creatingBookModel
        )
    }
}