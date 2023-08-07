package office.effective.features.booking.repository

import office.effective.common.exception.InstanceNotFoundException
import office.effective.common.exception.MissingIdException
import office.effective.features.booking.converters.BookingRepositoryConverter
import office.effective.features.user.repository.*
import office.effective.model.Booking
import office.effective.model.UserModel
import org.ktorm.database.Database
import org.ktorm.dsl.*
import org.ktorm.entity.*
import org.ktorm.support.postgresql.insertOrUpdate
import java.util.*
import kotlin.collections.List

class BookingRepository(private val database: Database, private val converter: BookingRepositoryConverter) {

    fun existsById(id: UUID): Boolean {
        var result = false
        database.from(WorkspaceBooking)
            .select(exists(database.from(WorkspaceBooking).select().where { WorkspaceBooking.id eq id }))
            .limit(1)
            .forEach { row -> result = row.getBoolean(1) }
        return result
    }

    fun deleteById(id: UUID) {
        database.workspaceBooking.removeIf { it.id eq id }
    }

    fun findById(bookingId: UUID): Booking? {
        val entity = database.workspaceBooking.find { it.id eq bookingId } ?: return null
        val participants = findParticipants(bookingId)
        return entity.let { converter.entityToModel(it, participants) }
    }

    fun findAllByOwnerId(ownerId: UUID): List<Booking> {
        val entityList = database.workspaceBooking.filter { it.ownerId eq ownerId }.toList()
        return entityList.map {
            val participants = findParticipants(it.id)
            converter.entityToModel(it, participants)
        }
    }

    private fun findParticipants(bookingId: UUID): List<UserEntity> {
        return database
            .from(BookingParticipants)
            .innerJoin(right = Users, on = BookingParticipants.userId eq Users.id)
            .select()
            .where { BookingParticipants.bookingId eq bookingId }
            .map { row -> Users.createEntity(row) }
    }

    fun save(booking: Booking): Booking {
        val id = UUID.randomUUID()
        booking.id = id
        val entity = converter.modelToEntity(booking)

        database.workspaceBooking.add(converter.modelToEntity(booking))

        val participantList = findParticipantEntities(booking.participants)
        for(participant in participantList) {
            database.insert(BookingParticipants) {
                set(it.bookingId, entity.id)
                set(it.userId, participant.id)
            }
        }
        return booking
    }

    fun update(booking: Booking): Booking {
        booking.id?.let {
            if(!existsById(it))
                throw InstanceNotFoundException(WorkspaceBookingEntity::class, "Booking with id $it not wound")
        }

        val entity = converter.modelToEntity(booking)
        database.workspaceBooking.update(entity)

        database.bookingParticipants.removeIf { it.bookingId eq entity.id }
        saveParticipants(booking.participants, entity.id)
        return booking
    }
    private fun saveParticipants(participantModels: List<UserModel>, bookingId: UUID): List<UserEntity> {
        val participantList = findParticipantEntities(participantModels)
        for(participant in participantList) {
            database.insert(BookingParticipants) {
                set(it.bookingId, bookingId)
                set(it.userId, participant.id)
            }
        }
        return participantList
    }
    private fun findParticipantEntities(participantModels: List<UserModel>): List<UserEntity> {
        val participantList = mutableListOf<UserEntity>()

        for (participant in participantModels) {
            val participantId: UUID = participant.id
                ?: throw MissingIdException("User with name ${ participant.fullName } doesn't have an id")

            val user: UserEntity = database.users.find { it.id eq participantId }
                ?: throw InstanceNotFoundException(UserEntity::class, "User with id $participantId not found", participantId)
            participantList.add(user)
        }
        return participantList
    }
}