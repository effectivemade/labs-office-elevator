package band.effective.office.elevator.domain.entity

import band.effective.office.elevator.domain.models.BookingInfo
import band.effective.office.elevator.domain.models.CreatingBookModel
import band.effective.office.elevator.domain.models.ErrorWithData
import band.effective.office.elevator.domain.repository.BookingRepository
import band.effective.office.elevator.domain.useCase.ChangeBookingUseCase
import band.effective.office.elevator.domain.useCase.CreateBookingUseCase
import band.effective.office.elevator.domain.useCase.GetBookingsUseCase
import band.effective.office.elevator.domain.useCase.WorkspacesUseCase
import band.effective.office.elevator.ui.booking.models.WorkSpaceUI
import band.effective.office.elevator.ui.booking.models.WorkspaceZoneUI
import band.effective.office.elevator.ui.employee.aboutEmployee.models.BookingsFilter
import band.effective.office.elevator.ui.models.ReservedSeat
import band.effective.office.network.model.Either
import io.github.aakira.napier.Napier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDateTime

class BookingInteractor(
    private val getBookingsUseCase: GetBookingsUseCase,
    private val changeBookingUseCase: ChangeBookingUseCase,
    private val createBookingUseCase: CreateBookingUseCase,
    private val workspaceUseCase: WorkspacesUseCase,
    private val repository: BookingRepository // todo replace this
) {
    suspend fun getForUser(
        ownerId:String? = null,
        beginDateTime: LocalDateTime,
        endDateTime: LocalDateTime,
        bookingsFilter: BookingsFilter
    ): Flow<Either<ErrorWithData<List<ReservedSeat>>, List<ReservedSeat>>> =
        getBookingsUseCase.getBookingsForUser(
            ownerId = ownerId,
            beginDateTime = beginDateTime,
            endDateTime = endDateTime,
            bookingsFilter = bookingsFilter
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
        freeUntil: LocalDateTime? = null,
        selectedWorkspacesZone: List<WorkspaceZoneUI>
    ) = workspaceUseCase
            .getWorkSpaces(
                tag = tag,
                freeFrom = freeFrom,
                freeUntil = freeUntil
        ).map { response ->
            when (response) {
                is Either.Success -> {
                    val list = selectedWorkspacesZone.filter { it.isSelected }
                    val listWorkSpaces = response.data

                    Napier.d { response.data.toString() }
                    val filteredWorkspacesList = mutableListOf<WorkSpaceUI>()

                    list.forEach { zone ->
                        val zoneName = zone.name
                        val foundedSpace =
                            listWorkSpaces.filter { it.zoneName == zoneName }

                        filteredWorkspacesList.addAll(foundedSpace)
                    }
                    Either.Success(filteredWorkspacesList.toList())
                }

                is Either.Error -> {
                    Napier.e {"ERROORRR!!!! ${response.error.error}"}
                    Either.Error(response.error)
                }
            }
        }

    suspend fun deleteBooking(bookingId: String) = repository.deleteBooking(bookingId)
}