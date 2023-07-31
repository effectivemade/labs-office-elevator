package office.effective.features.booking.converters

import office.effective.features.booking.repository.WorkspaceBookingEntity
import office.effective.features.user.converters.UserModelEntityConverter
import office.effective.features.user.repository.UserEntity
import office.effective.features.user.repository.users
import office.effective.features.workspace.converters.WorkspaceRepositoryConverter
import office.effective.model.Booking
import org.ktorm.database.Database

class BookingRepositoryConverter(private val workspaceConverter: WorkspaceRepositoryConverter,
                                 private val userConverter: UserModelEntityConverter) {

    /**
     * Converts booking entity to model which contains user and workspace models.
     * User/workspace models contain an empty list of integrations/utilities.
     */
    fun entityToModel(bookingEntity: WorkspaceBookingEntity,
                      participants: List<UserEntity>): Booking {
        val ownerModel = userConverter.EntityToModel(bookingEntity.owner, emptySet())
        val participantModels = participants.map { userConverter.EntityToModel(it, emptySet()) }
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
     */
    fun modelToEntity(bookingModel: Booking): WorkspaceBookingEntity {
        return WorkspaceBookingEntity {
            owner = userConverter.modelToEntity(bookingModel.owner)
            workspace = workspaceConverter.modelToEntity(bookingModel.workspace)
            id = bookingModel.id
            beginBooking = bookingModel.beginBooking
            endBooking = bookingModel.endBooking
        }
    }
}