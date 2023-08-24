package band.effective.office.elevator.domain.entity

import band.effective.office.elevator.domain.models.BookingInfoDomain
import band.effective.office.elevator.domain.models.CreatingBookModel
import band.effective.office.elevator.domain.models.ErrorWithData
import band.effective.office.elevator.domain.useCase.ChangeBookingUseCase
import band.effective.office.elevator.domain.useCase.CreateBookingUseCase
import band.effective.office.elevator.domain.useCase.GetBookingsUseCase
import band.effective.office.elevator.ui.employee.aboutEmployee.models.BookingsFilter
import band.effective.office.elevator.ui.models.ReservedSeat
import band.effective.office.network.model.Either
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate

class BookingInteractor(
    private val getBookingsUseCase: GetBookingsUseCase,
    private val changeBookingUseCase: ChangeBookingUseCase,
    private val createBookingUseCase: CreateBookingUseCase
) {
    suspend fun getForUser(ownerId:String): Flow<Either<ErrorWithData<List<ReservedSeat>>, List<ReservedSeat>>> =
        getBookingsUseCase.getBookingsForUser(
            ownerId = ownerId,
            bookingsFilter = BookingsFilter(meetRoom = true, workPlace = true)
        )


    suspend fun getByDate(
        ownerId: String,
        date: LocalDate,
    ): Flow<Either<ErrorWithData<List<ReservedSeat>>, List<ReservedSeat>>> =
        getBookingsUseCase.getBookingsByDate(
            date = date, ownerId = ownerId,
            bookingsFilter = BookingsFilter(meetRoom = true, workPlace = true)
        )

    suspend fun change(bookingInfo: BookingInfoDomain) {
        changeBookingUseCase.execute(
            bookingInfo = bookingInfo
        )
    }

    suspend fun create(coroutineScope: CoroutineScope, creatingBookModel: CreatingBookModel) {
        createBookingUseCase.execute(
            coroutineScope = coroutineScope,
            creatingBookModel = creatingBookModel
        )
    }
}