package office.effective.features.booking.converters

import office.effective.common.exception.InstanceNotFoundException
import office.effective.common.exception.MissingIdException
import office.effective.features.booking.repository.WorkspaceBookingEntity
import office.effective.features.user.converters.UserModelEntityConverter
import office.effective.features.user.repository.UserEntity
import office.effective.features.user.repository.users
import office.effective.features.workspace.converters.WorkspaceRepositoryConverter
import office.effective.features.workspace.repository.WorkspaceEntity
import office.effective.features.workspace.repository.workspaces
import office.effective.model.Booking
import office.effective.model.UserModel
import office.effective.model.Workspace
import org.ktorm.database.Database
import org.ktorm.dsl.eq
import org.ktorm.entity.find
import java.util.*

class BookingRepositoryConverter(private val database: Database,
                                 private val workspaceConverter: WorkspaceRepositoryConverter,
                                 private val userConverter: UserModelEntityConverter) {

    /**
     * Converts booking entity to model which contains user and workspace models.
     * User/workspace models contain an empty list of integrations/utilities.
     */
    fun entityToModel(bookingEntity: WorkspaceBookingEntity,
                      participants: List<UserEntity>): Booking {
        val ownerModel = userConverter.entityToModel(bookingEntity.owner, emptySet())
        val participantModels = participants.map { userConverter.entityToModel(it, emptySet()) }
        val workspaceModel = workspaceConverter.entityToModel(bookingEntity.workspace, emptyList())
        return Booking (
            ownerModel,
            participantModels,
            workspaceModel,
            bookingEntity.id,
            bookingEntity.beginBooking,
            bookingEntity.endBooking
        )
    }

    /**
     * Converts booking model to entity
     *
     * Throws MissingIdException if booking id is null
     */
    fun modelToEntity(bookingModel: Booking): WorkspaceBookingEntity {
        return WorkspaceBookingEntity {
            owner = findOwnerEntity(bookingModel.owner)
            workspace = findWorkspaceEntity(bookingModel.workspace)
            id = bookingModel.id ?: throw MissingIdException("Booking model doesn't have an id")
            beginBooking = bookingModel.beginBooking
            endBooking = bookingModel.endBooking
        }
    }

    private fun findOwnerEntity(ownerModel: UserModel): UserEntity {
        val ownerId: UUID = ownerModel.id
            ?: throw MissingIdException("User with name ${ ownerModel.fullName } doesn't have an id")

        return database.users.find { it.id eq ownerId }
            ?: throw InstanceNotFoundException(UserEntity::class, "User with id $ownerId not found", ownerId)
    }

    private fun findWorkspaceEntity(workspaceModel: Workspace): WorkspaceEntity {
        val workspaceId: UUID = workspaceModel.id
            ?: throw MissingIdException("Workspace with name ${ workspaceModel.name } doesn't have an id")

        return database.workspaces.find { it.id eq workspaceId }
            ?: throw InstanceNotFoundException(UserEntity::class, "Workspace with id $workspaceId not found", workspaceId)
    }
}