package office.effective.features.booking.service

import office.effective.common.exception.InstanceNotFoundException
import office.effective.common.exception.MissingIdException
import office.effective.features.booking.repository.BookingRepository
import office.effective.features.user.repository.UserEntity
import office.effective.features.user.repository.UserRepository
import office.effective.features.workspace.repository.WorkspaceRepository
import office.effective.model.*
import java.util.UUID

class BookingService(private val bookingRepository: BookingRepository,
                     private val userRepository: UserRepository,
                     private val workspaceRepository: WorkspaceRepository) {

    /**
     * Returns whether a booking with the given id exists
     *
     * @author Daniil Zavyalov
     */
    fun existsById(id: UUID): Boolean {
        return bookingRepository.existsById(id)
    }

    /**
     * Deletes the booking with the given id
     *
     * @author Daniil Zavyalov
     */
    fun deleteById(id: UUID) {
        bookingRepository.deleteById(id)
    }

    /**
     * Retrieves a booking model by its id
     *
     * @author Daniil Zavyalov
     */
    fun findById(id: UUID): Booking? {
        val booking = bookingRepository.findById(id)
        booking?.let {
            for (participant in it.participants) {
                participant.integrations = findIntegrations(participant)
            }
            it.owner.integrations = findIntegrations(it.owner)
            it.workspace.utilities = findUtilities(it.workspace)
        }
        return booking
    }

    /**
     * Returns all bookings. Bookings can be filtered by owner and workspace id
     *
     * @throws InstanceNotFoundException if user or workspace with the given id doesn't exist in database
     *
     * @author Daniil Zavyalov
     */
    fun findAll(userId: UUID?, workspaceId: UUID?): List<Booking> {
        val bookingList = when {
            userId != null && workspaceId != null -> {
                if (!workspaceRepository.workspaceExistsById(workspaceId))
                    throw InstanceNotFoundException(UserEntity::class, "User with id $workspaceId not found", workspaceId)
                if (!userRepository.existsById(userId))
                    throw InstanceNotFoundException(UserEntity::class, "User with id $userId not found", userId)
                bookingRepository.findAllByOwnerAndWorkspaceId(userId, workspaceId)
            }
            userId != null -> {
                if (!userRepository.existsById(userId))
                    throw InstanceNotFoundException(UserEntity::class, "User with id $userId not found", userId)
                bookingRepository.findAllByOwnerId(userId)
            }
            workspaceId != null -> {
                if (!workspaceRepository.workspaceExistsById(workspaceId))
                    throw InstanceNotFoundException(UserEntity::class, "User with id $workspaceId not found", workspaceId)
                bookingRepository.findAllByWorkspaceId(workspaceId)
            }
            else -> bookingRepository.findAll()
        }
        return addIntegrationsAndUtilities(bookingList)
    }

    /**
     * Adds integrations and utilities to related user and workspace models.
     * Use the returned booking list for further operations
     *
     * @throws MissingIdException if user or workspace doesn't have an id
     *
     * @author Daniil Zavyalov
     */
    private fun addIntegrationsAndUtilities(bookingList: List<Booking>): List<Booking> {
        for (booking in bookingList) {
            for (participant in booking.participants) {
                participant.integrations = findIntegrations(participant)
            }
            booking.owner.integrations = findIntegrations(booking.owner)
            booking.workspace.utilities = findUtilities(booking.workspace)
        }
        return bookingList
    }

    /**
     * Retrieves all integrations for a given user model
     *
     * @throws MissingIdException if user model doesn't have an id
     *
     * @author Daniil Zavyalov
     */
    private fun findIntegrations(user: UserModel): Set<IntegrationModel> {
        val userId = user.id
            ?: throw MissingIdException("User with name ${ user.fullName } doesn't have an id")
        return userRepository.findSetOfIntegrationsByUser(userId)
    }

    /**
     * Retrieves all utilities for a given workspace model
     *
     * @throws MissingIdException if workspace doesn't have an id
     *
     * @author Daniil Zavyalov
     */
    private fun findUtilities(workspace: Workspace): List<Utility> {
        val workspaceId = workspace.id
            ?: throw MissingIdException("Workspace with name ${ workspace.name } doesn't have an id")
        return workspaceRepository.findUtilitiesByWorkspaceId(workspaceId)
    }

    /**
     * Saves a given booking. Use the returned model for further operations
     *
     * @author Daniil Zavyalov
     */
    fun save(booking: Booking): Booking {
        return bookingRepository.save(booking)
    }

    /**
     * Updates a given booking. Use the returned model for further operations
     *
     * @author Daniil Zavyalov
     */
    fun update(booking: Booking): Booking {
        return bookingRepository.update(booking)
    }
}