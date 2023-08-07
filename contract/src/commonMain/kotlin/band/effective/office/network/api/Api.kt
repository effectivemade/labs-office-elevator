package band.effective.office.network.api

import band.effective.office.network.dto.BookingInfo
import band.effective.office.network.dto.SuccessResponse
import band.effective.office.network.dto.UserDTO
import band.effective.office.network.dto.WorkspaceDTO
import band.effective.office.network.model.Either
import band.effective.office.network.model.ErrorResponse
import kotlinx.coroutines.flow.Flow

interface Api {
    /**Get workspace by id
     * @param id workspace id
     * @return if response is success when return workspace info*/
    suspend fun getWorkspace(id: String): Either<ErrorResponse, WorkspaceDTO>

    /**Get all workspace current type
     * @param tag workspace type. Meeting or regular
     * @return if response is success when return list of workspaces*/
    suspend fun getWorkspaces(tag: String): Either<ErrorResponse, List<WorkspaceDTO>>

    /**Get user by id
     * @param id user id
     * @return if response is success when return user info*/
    suspend fun getUser(id: String): Either<ErrorResponse, UserDTO>

    /**Get all users
     * @return if response is success when return users list*/
    suspend fun getUsers(): Either<ErrorResponse, List<UserDTO>>

    /**Get user's bookings*/
    suspend fun getBookingsByUser(userId: String): Either<ErrorResponse, List<BookingInfo>>

    /**Get bookings in workspace*/
    suspend fun getBookingsByWorkspaces(workspaceId: String): Either<ErrorResponse, List<BookingInfo>>

    /**Booking workspace*/
    suspend fun createBooking(bookingInfo: BookingInfo): Either<ErrorResponse, SuccessResponse>

    /**Update booking info*/
    suspend fun updateBooking(
        bookingInfo: BookingInfo
    ): Either<ErrorResponse, SuccessResponse>

    /**Delete booking*/
    suspend fun deleteBooking(
        bookingId: String
    ): Either<ErrorResponse, SuccessResponse>

    /**Subscribe on workspace info updates*/
    suspend fun subscribeOnWorkspaceUpdates(id: String): Flow<Either<ErrorResponse, WorkspaceDTO>>

    /**Subscribe on organizers list updates*/
    suspend fun subscribeOnOrganizersList(): Flow<Either<ErrorResponse, List<UserDTO>>>

    /**Subscribe on bookings list updates*/
    suspend fun subscribeOnBookingsList(workspaceId: String): Flow<Either<ErrorResponse, List<BookingInfo>>>
}