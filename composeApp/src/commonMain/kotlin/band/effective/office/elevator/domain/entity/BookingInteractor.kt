package band.effective.office.elevator.domain.entity

import band.effective.office.elevator.domain.models.BookingInfo
import band.effective.office.elevator.domain.models.CreatingBookModel
import band.effective.office.elevator.domain.models.ErrorWithData
import band.effective.office.elevator.domain.repository.BookingRepository
import band.effective.office.elevator.domain.useCase.ChangeBookingUseCase
import band.effective.office.elevator.domain.useCase.CreateBookingUseCase
import band.effective.office.elevator.domain.useCase.GetBookingsUseCase
import band.effective.office.elevator.domain.useCase.WorkspacesUseCase
import band.effective.office.elevator.ui.employee.aboutEmployee.models.BookingsFilter
import band.effective.office.elevator.ui.models.ReservedSeat
import band.effective.office.network.model.Either
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

class BookingInteractor(
    private val getBookingsUseCase: GetBookingsUseCase,
    private val changeBookingUseCase: ChangeBookingUseCase,
    private val createBookingUseCase: CreateBookingUseCase,
    private val workspaceUseCase: WorkspacesUseCase,
    private val repository: BookingRepository // todo replace this
) {
    suspend fun getForUser(ownerId:String): Flow<Either<ErrorWithData<List<ReservedSeat>>, List<ReservedSeat>>> =
        getBookingsUseCase.getBookingsForUser(
            ownerId = ownerId,
            bookingsFilter = BookingsFilter(meetRoom = true, workPlace = true)
        )


    suspend fun getByDate(
        date: LocalDate,
    ): Flow<Either<ErrorWithData<List<ReservedSeat>>, List<ReservedSeat>>> =
        getBookingsUseCase.getBookingsByDate(
            date = date,
            bookingsFilter = BookingsFilter(meetRoom = true, workPlace = true)
        )

    suspend fun change(bookingInfo: BookingInfo) {
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

    suspend fun getZones() = workspaceUseCase.getZones()
    suspend fun getWorkspaces(
        tag: String,
        freeFrom: LocalDateTime? = null,
        freeUntil: LocalDateTime? = null
    ) = workspaceUseCase.getWorkSpaces(
        tag = tag,
        freeFrom = freeFrom,
        freeUntil = freeUntil
    )

    suspend fun deleteBooking(bookingId: String) = repository.deleteBooking(bookingId)
}