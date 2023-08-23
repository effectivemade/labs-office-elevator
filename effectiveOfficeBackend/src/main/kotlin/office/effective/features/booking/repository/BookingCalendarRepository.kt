package office.effective.features.booking.repository

import com.google.api.services.calendar.Calendar
import com.google.api.services.calendar.model.Event
import office.effective.common.exception.InstanceNotFoundException
import office.effective.common.exception.MissingIdException
import office.effective.features.booking.converters.BookingRepositoryConverter
import office.effective.features.calendar.repository.CalendarRepository
import office.effective.features.user.repository.UserRepository
import office.effective.features.workspace.repository.WorkspaceRepository
import office.effective.model.Booking
import office.effective.model.IntegrationModel
import org.ktorm.database.Database
import utils.GoogleCalendarConverter
import utils.GoogleCalendarConverter.toBookingModel
import utils.GoogleCalendarConverter.toGoogleEvent
import java.util.*

class BookingCalendarRepository(
    private val calendarRepository: CalendarRepository,
    private val userRepository: UserRepository,
    private val calendar : Calendar
) : IBookingRepository {
    private val calendarEvents = calendar.Events();

    private fun getCalendarIdByWorkspace(workspaceId: UUID): String {
        return calendarRepository.findByWorkspace(workspaceId)
    }

    override fun existsById(id: String): Boolean {
        var event: Any? = null
        calendarRepository.findAllCalendarsId().forEach {
            event = calendarEvents.get(it, id).execute()
        }
        return event != null
    }

    override fun deleteById(id: String) {
        calendarRepository.findAllCalendarsId().forEach {
            calendarEvents.delete(it, id).execute()
        }
    }

    override fun findById(bookingId: String): Booking? {
        var event: Event? = null;
        calendarRepository.findAllCalendarsId().forEach {
            event = findByCalendarIdAndBookingId(it, bookingId)
        }
        return event?.toBookingModel()
    }

    private fun findByCalendarIdAndBookingId(calendarId: String, bookingId: String): Event? {
        return calendarEvents.list(calendarId).execute().items.find { it.id.equals(bookingId.toString()) }
    }

    override fun findAllByOwnerId(ownerId: UUID): List<Booking> {
        val userEmail: String = findUserEmailByUserId(ownerId)
        val eventsList = mutableListOf<Event>()
        calendarRepository.findAllCalendarsId().forEach {
            eventsList.addAll(calendarEvents.list(it).execute().items.filter { it.organizer.email.equals(userEmail) })
        }
        val bookingList = mutableListOf<Booking>()
        eventsList.forEach { bookingList.add(it.toBookingModel()) }
        return bookingList;
    }

    private fun findUserEmailByUserId(id: UUID): String {
        return userRepository.findSetOfIntegrationsByUser(id).find { it.name.equals("email") }?.valueStr
            ?: throw InstanceNotFoundException(
                IntegrationModel::class, "Email integration for user $id cannot be found ", null
            );
    }

    override fun findAllByWorkspaceId(workspaceId: UUID): List<Booking> {
        val calendarId = getCalendarIdByWorkspace(workspaceId)
        val bookingList = mutableListOf<Booking>()
        calendarEvents.list(calendarId).execute().items.forEach { bookingList.add(it.toBookingModel()) }
        return bookingList
    }

    override fun findAllByOwnerAndWorkspaceId(ownerId: UUID, workspaceId: UUID): List<Booking> {
        val email = findUserEmailByUserId(ownerId)
        return findAllByWorkspaceId(workspaceId).filter {
            it.owner.integrations?.find { it.name.equals("email") }?.valueStr.equals(
                email
            )
        }
    }

    override fun findAll(): List<Booking> {
        val bookingList = mutableListOf<Booking>()
        calendarRepository.findAllCalendarsId().forEach {
            calendarEvents.list(it).execute().items.forEach { bookingList.add(it.toBookingModel()) }
        }
        return bookingList
    }

    override fun save(booking: Booking): Booking {

        val event = booking.toGoogleEvent()
        val calendarID: String =
            calendarRepository.findByWorkspace(booking.workspace.id ?: throw MissingIdException("workspace model"))
        calendar.Events().insert("effective.office@effective.band", event).execute()

        return findById(event.id) ?: throw Exception("Calendar save goes wrong")
    }

    override fun update(booking: Booking): Booking {
        val event = booking.toGoogleEvent()
        booking.workspace.id
            ?: throw MissingIdException("Workspace ${booking.workspace.name} must have id to push event ${event.id}")
        val id = getCalendarIdByWorkspace(booking.workspace.id!!)
        calendarEvents.update(id, event.id, event)

        return findById(booking.id ?: throw MissingIdException("Booking must have id to update event"))
            ?: throw InstanceNotFoundException(
                Booking::class, "Cannot to found booking directly after booking", null
            )
    }
}