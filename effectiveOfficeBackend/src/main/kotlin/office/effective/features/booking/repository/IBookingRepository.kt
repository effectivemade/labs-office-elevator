package office.effective.features.booking.repository

import office.effective.common.exception.InstanceNotFoundException
import office.effective.common.exception.MissingIdException
import office.effective.common.exception.WorkspaceUnavailableException
import office.effective.features.user.repository.UserEntity
import office.effective.features.user.repository.Users
import office.effective.features.user.repository.users
import office.effective.model.Booking
import office.effective.model.UserModel
import org.ktorm.dsl.*
import org.ktorm.entity.*
import java.util.*

interface IBookingRepository {
    /**
     * Returns whether a booking with the given id exists
     *
     * @author Daniil Zavyalov
     */
    fun existsById(id: String): Boolean ;

    /**
     * Deletes the booking with the given id
     *
     * If the booking is not found in the database it is silently ignored
     *
     * @author Daniil Zavyalov
     */
    fun deleteById(id: String) ;

    /**
     * Retrieves a booking model by its id.
     * Retrieved booking contains user and workspace models without integrations and utilities
     *
     * @author Daniil Zavyalov
     */
    fun findById(bookingId: String): Booking? ;

    /**
     * Returns all bookings with the given owner id
     *
     * @author Daniil Zavyalov
     */
    fun findAllByOwnerId(ownerId: UUID): List<Booking> ;

    /**
     * Returns all bookings with the given workspace id
     *
     * @author Daniil Zavyalov
     */
    fun findAllByWorkspaceId(workspaceId: UUID): List<Booking> ;

    /**
     * Returns all bookings with the given workspace and owner id
     *
     * @author Daniil Zavyalov
     */
    fun findAllByOwnerAndWorkspaceId(ownerId: UUID, workspaceId: UUID): List<Booking> ;

    /**
     * Returns all bookings
     *
     * @author Daniil Zavyalov
     */
    fun findAll(): List<Booking> ;

    /**
     * Retrieves all users who participate in the booking with the given id
     *
     * @author Daniil Zavyalov
     */
//    private fun findParticipants(bookingId: UUID): List<UserEntity> ;

    /**
     * Checks whether the workspace is available for booking for the given period
     *
     * @author Daniil Zavyalov
     */
//    private fun workspaceAvailableForBooking(bookingEntity: WorkspaceBookingEntity): Boolean;

    /**
     * Saves a given booking. If given model will have an id, it will be ignored.
     * Use the returned model for further operations
     *
     * @throws WorkspaceUnavailableException if workspace can't be booked in a given period
     *
     * @author Daniil Zavyalov
     */
    fun save(booking: Booking): Booking ;

    /**
     * Checks whether the booking can be updated with the given booking period
     *
     * @author Daniil Zavyalov
     */
//    private fun bookingCanBeUpdated(entity: WorkspaceBookingEntity): Boolean ;

    /**
     * Updates a given booking. Use the returned model for further operations
     *
     * @throws InstanceNotFoundException if booking given id doesn't exist in the database
     *
     * @throws WorkspaceUnavailableException if workspace can't be booked in a given period
     *
     * @author Daniil Zavyalov
     */
    fun update(booking: Booking): Booking ;

    /**
     * Adds many-to-many relationship between booking and its participants
     *
     * @author Daniil Zavyalov
     */
//    private fun saveParticipants(participantModels: List<UserModel>, bookingId: UUID): List<UserEntity> ;

    /**
     * Retrieves user entities by the ids of the given user models
     *
     * @author Daniil Zavyalov
     */
//    private fun findParticipantEntities(participantModels: List<UserModel>): List<UserEntity> ;
}