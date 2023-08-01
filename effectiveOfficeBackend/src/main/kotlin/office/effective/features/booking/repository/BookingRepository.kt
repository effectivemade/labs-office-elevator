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

    fun findAllByOwnerId(ownerId: UUID): List<Booking> {
        val entityList = database.workspaceBooking.filter { it.ownerId eq ownerId }.toList()
        return entityList.map {
            val participants = findParticipants(it.id)
            converter.entityToModel(it, participants)
        }
    }

    fun deleteById(id: UUID) {
        database.workspaceBooking.removeIf { it.id eq id }
    }

    fun save(booking: Booking): Booking {
        val id = UUID.randomUUID()
        booking.id = id
        val entity = converter.modelToEntity(booking)

        database.workspaceBooking.add(converter.modelToEntity(booking))

        val participantList = findParticipantEntities(booking.participants)
        for(participant in participantList) {
            database.bookingParticipants.add(
                BookingParticipantEntity {
                    this.user = participant
                    this.booking = entity
                }
            )
        }
        return booking
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