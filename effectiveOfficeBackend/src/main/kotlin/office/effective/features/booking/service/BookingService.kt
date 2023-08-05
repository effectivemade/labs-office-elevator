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

    fun existsById(id: UUID): Boolean {
        return bookingRepository.existsById(id)
    }

    fun deleteById(id: UUID) {
        bookingRepository.deleteById(id)
    }

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

    fun findAllByOwnerId(ownerId: UUID): List<Booking> {
        if (!userRepository.existsById(ownerId))
            throw InstanceNotFoundException(UserEntity::class, "User with id $ownerId not found", ownerId)
        val bookingList = bookingRepository.findAllByOwnerId(ownerId)
        return addIntegrationsAndUtilities(bookingList)
    }

    fun findAllByWorkspaceId(workspaceId: UUID): List<Booking> {
        if (!workspaceRepository.workspaceExistsById(workspaceId))
            throw InstanceNotFoundException(UserEntity::class, "User with id $workspaceId not found", workspaceId)
        val bookingList = bookingRepository.findAllByWorkspaceId(workspaceId)
        return addIntegrationsAndUtilities(bookingList)
    }

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

    private fun findIntegrations(user: UserModel): Set<IntegrationModel> {
        val userId = user.id
            ?: throw MissingIdException("User with name ${ user.fullName } doesn't have an id")
        return userRepository.findSetOfIntegrationsByUser(userId)
    }

    private fun findUtilities(workspace: Workspace): List<Utility> {
        val workspaceId = workspace.id
            ?: throw MissingIdException("Workspace with name ${ workspace.name } doesn't have an id")
        return workspaceRepository.findUtilitiesByWorkspaceId(workspaceId)
    }

    fun save(booking: Booking): Booking {
        return bookingRepository.save(booking)
    }

    fun update(booking: Booking): Booking {
        return bookingRepository.update(booking)
    }
}