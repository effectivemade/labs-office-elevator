package office.effective.features.booking.repository

import office.effective.features.booking.converters.BookingRepositoryConverter
import office.effective.features.user.repository.*
import office.effective.features.workspace.repository.Utilities
import office.effective.features.workspace.repository.WorkspaceTags
import office.effective.features.workspace.repository.WorkspaceUtilities
import office.effective.features.workspace.repository.Workspaces
import office.effective.model.Booking
import office.effective.model.UserModel
import office.effective.model.Utility
import org.ktorm.database.Database
import org.ktorm.dsl.*
import org.ktorm.entity.find
import java.util.*
import kotlin.collections.List

class BookingRepository(private val database: Database, private val converter: BookingRepositoryConverter) {

    fun findById(bookingId: UUID): Booking? {
        val entity = database.workspaceBooking.find { it.id eq bookingId } ?: return null
        val participants = findParticipants(bookingId)
        return entity.let { converter.entityToModel(it, participants) }
    }

    private fun findParticipants(bookingId: UUID): List<UserEntity> {
        return database
            .from(BookingParticipants)
            .innerJoin(right = Users, on = BookingParticipants.userId eq Users.id)
            .select()
            .where { BookingParticipants.bookingId eq bookingId }
            .map { row -> Users.createEntity(row) }
    }
}