package band.effective.office.network.api

import band.effective.office.network.dto.BookingDTO
import band.effective.office.network.dto.SuccessResponse
import band.effective.office.network.dto.UserDTO
import band.effective.office.network.dto.WorkspaceDTO
import band.effective.office.network.dto.WorkspaceZoneDTO
import band.effective.office.network.model.Either
import band.effective.office.network.model.ErrorResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface Api {
    /**Get workspace by id
     * @param id workspace id
     * @return if response is success when return workspace info*/
    suspend fun getWorkspace(id: String): Either<ErrorResponse, WorkspaceDTO>

    /**Get all workspace current type
     * @param tag workspace type. Meeting or regular
     * @return if response is success when return list of workspaces*/
    suspend fun getWorkspaces(
        tag: String,
        freeFrom: Long? = null,
        freeUntil: Long? = null
    ): Either<ErrorResponse, List<WorkspaceDTO>>

    suspend fun getZones(): Either<ErrorResponse, List<WorkspaceZoneDTO>>

    /**Get user by id
     * @param id user id
     * @return if response is success when return user info*/
    suspend fun getUser(id: String): Either<ErrorResponse, UserDTO>

    /**Get all users
     * @return if response is success when return users list*/
    suspend fun getUsers(tag: String): Either<ErrorResponse, List<UserDTO>>

    suspend fun updateUser(user: UserDTO): Either<ErrorResponse, UserDTO>

    suspend fun getBooking(id: String): Either<ErrorResponse, BookingDTO>

    /**Get user's bookings*/
    suspend fun getBookingsByUser(userId: String): Either<ErrorResponse, List<BookingDTO>>

    /**Get bookings in workspace*/
    suspend fun getBookingsByWorkspaces(workspaceId: String): Either<ErrorResponse, List<BookingDTO>>

    /**Booking workspace*/
    suspend fun createBooking(bookingInfo: BookingDTO): Either<ErrorResponse, SuccessResponse>

    /**Update booking info*/
    suspend fun updateBooking(
        bookingInfo: BookingDTO
    ): Either<ErrorResponse, SuccessResponse>

    /**Delete booking*/
    suspend fun deleteBooking(
        bookingId: String
    ): Either<ErrorResponse, SuccessResponse>

    /**Subscribe on workspace info updates*/
    fun subscribeOnWorkspaceUpdates(
        id: String,
        scope: CoroutineScope
    ): Flow<Either<ErrorResponse, WorkspaceDTO>>

    /**Subscribe on organizers list updates*/
    fun subscribeOnOrganizersList(scope: CoroutineScope): Flow<Either<ErrorResponse, List<UserDTO>>>

    /**Subscribe on bookings list updates*/
    fun subscribeOnBookingsList(
        workspaceId: String,
        scope: CoroutineScope
    ): Flow<Either<ErrorResponse, List<BookingDTO>>>

    suspend fun getUserByEmail(email: String): Either<ErrorResponse, UserDTO>
}