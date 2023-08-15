package band.effective.office.network.api.impl

import band.effective.office.network.api.Api
import band.effective.office.network.dto.BookingInfo
import band.effective.office.network.dto.SuccessResponse
import band.effective.office.network.dto.UserDTO
import band.effective.office.network.dto.WorkspaceDTO
import band.effective.office.network.model.Either
import band.effective.office.network.model.ErrorResponse
import band.effective.office.utils.MockFactory
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ApiMock(private val realApi: Api, mockFactory: MockFactory) : Api {
    var getRealResponse: Boolean = false
    private val workspaces = mockFactory.workspaces()
    private val meetingRooms = mockFactory.meetingRooms()
    private val users = MutableStateFlow(mockFactory.users())
    private val bookings = MutableStateFlow(mockFactory.bookings())
    private val successResponse = mockFactory.success()

    private fun <T> response(mock: T?, realResponse: Either<ErrorResponse, T>) =
        with(getRealResponse) {
            when {
                this && !(realResponse.requestNotExist()) -> realResponse
                mock == null -> Either.Error(ErrorResponse.getResponse(404))
                else -> Either.Success(mock)
            }
        }

    private fun <T> Either<ErrorResponse, T>.requestNotExist() =
        this is Either.Error && error.code in 600..699

    override suspend fun getWorkspace(id: String): Either<ErrorResponse, WorkspaceDTO> = response(
        mock = (workspaces + meetingRooms).firstOrNull() { it.id == id },
        realResponse = realApi.getWorkspace(id)
    )

    override suspend fun getWorkspaces(tag: String): Either<ErrorResponse, List<WorkspaceDTO>> =
        response(
            mock = if (tag == "meeting") meetingRooms else workspaces,
            realResponse = realApi.getWorkspaces(tag = tag)
        )

    override suspend fun getUser(id: String): Either<ErrorResponse, UserDTO> = response(
        mock = users.value.firstOrNull { it.id == id },
        realResponse = realApi.getUser(id)
    )

    override suspend fun getUsers(): Either<ErrorResponse, List<UserDTO>> = response(
        mock = users.value,
        realResponse = realApi.getUsers()
    )

    override suspend fun getBookingsByUser(userId: String): Either<ErrorResponse, List<BookingInfo>> =
        response(
            mock = bookings.value.filter { it.ownerId == userId },
            realResponse = realApi.getBookingsByUser(userId = userId)
        )

    override suspend fun getBookingsByWorkspaces(workspaceId: String): Either<ErrorResponse, List<BookingInfo>> =
        response(
            mock = bookings.value.filter { it.workspaceId == workspaceId },
            realResponse = realApi.getBookingsByWorkspaces(workspaceId = workspaceId)
        )

    override suspend fun createBooking(bookingInfo: BookingInfo): Either<ErrorResponse, SuccessResponse> =
        response(
            mock = successResponse.apply { bookings.update { it + bookingInfo } },
            realResponse = realApi.createBooking(bookingInfo)
        )

    override suspend fun updateBooking(
        bookingInfo: BookingInfo
    ): Either<ErrorResponse, SuccessResponse> = response(
        mock = successResponse.apply { bookings.update { it.map { element -> if (element.id == bookingInfo.id) bookingInfo else element } } },
        realResponse = realApi.updateBooking(bookingInfo)
    )

    override suspend fun deleteBooking(
        bookingId: String
    ): Either<ErrorResponse, SuccessResponse> = response(
        mock = successResponse.apply { bookings.update { it.filter { element -> element.id != bookingId } } },
        realResponse = realApi.deleteBooking(bookingId)
    )

    override suspend fun subscribeOnWorkspaceUpdates(id: String): Flow<Either<ErrorResponse, WorkspaceDTO>> =
        flow {
            realApi.subscribeOnWorkspaceUpdates(id).collect { if (getRealResponse) emit(it) }
        }

    override suspend fun subscribeOnOrganizersList(): Flow<Either<ErrorResponse, List<UserDTO>>> =
        channelFlow {
            launch { users.collect { if (!getRealResponse) send(Either.Success(it)) } }
            launch {
                realApi.subscribeOnOrganizersList().collect { if (getRealResponse) send(it) }
            }
            awaitClose()
        }

    override suspend fun subscribeOnBookingsList(workspaceId: String): Flow<Either<ErrorResponse, List<BookingInfo>>> =
        channelFlow {
            launch {
                bookings.collect {
                    if (!getRealResponse) send(Either.Success(it.filter { item -> item.workspaceId == workspaceId }))
                }
            }
            launch {
                realApi.subscribeOnBookingsList(workspaceId)
                    .collect { if (getRealResponse) send(it) }
            }
            awaitClose()
        }
}