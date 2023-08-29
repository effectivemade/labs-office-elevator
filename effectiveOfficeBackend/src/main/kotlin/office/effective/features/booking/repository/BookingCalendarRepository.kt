package office.effective.features.booking.repository

import com.google.api.services.calendar.Calendar
import com.google.api.services.calendar.model.Event
import office.effective.common.exception.InstanceNotFoundException
import office.effective.common.exception.MissingIdException
import office.effective.config
import office.effective.features.calendar.repository.CalendarRepository
import office.effective.features.user.repository.UserRepository
import office.effective.model.Booking
import office.effective.features.calendar.service.GoogleCalendarConverter.toBookingModel
import office.effective.features.calendar.service.GoogleCalendarConverter.toGoogleEvent
import office.effective.features.user.repository.UserEntity
import office.effective.features.workspace.repository.WorkspaceRepository
import java.util.*

class BookingCalendarRepository(
    private val calendarRepository: CalendarRepository,
    private val userRepository: UserRepository,
    private val calendar: Calendar,
    private val workspaceRepository: WorkspaceRepository
) : IBookingRepository {
    private val calendarEvents = calendar.Events()
    private val defaultCalendar = config.propertyOrNull("auth.app.defaultAppEmail")?.getString()
        ?: throw Exception("Config file does not contain default gmail value")
    private val minTime = config.propertyOrNull("calendar.minTime")?.getString()?.toLong()
        ?: throw Exception("Config file does not contain minimum time")

    private fun getCalendarIdByWorkspace(workspaceId: UUID): String {
        return try {
            calendarRepository.findByWorkspace(workspaceId)
        } catch (e: InstanceNotFoundException) {
            defaultCalendar
        }
    }

    override fun existsById(id: String): Boolean {
        val event: Any?
        event = calendarEvents.get(defaultCalendar, id).execute()
        return event != null
    }

    override fun deleteById(id: String) {
        calendarEvents.delete(defaultCalendar, id).execute()
    }

    override fun findById(bookingId: String): Booking? {
        val event: Event? = findByCalendarIdAndBookingId(defaultCalendar, bookingId)
        return event?.toBookingModel()
    }

    private fun findByCalendarIdAndBookingId(calendarId: String, bookingId: String): Event? {
        return calendarEvents.list(calendarId).execute().items.find { it.id.equals(bookingId) }
    }

    override fun findAllByOwnerId(ownerId: UUID): List<Booking> {
        val userEmail: String = findUserEmailByUserId(ownerId)
        val eventsList = mutableListOf<Event>()
        findAllEntities().filter { checkEventOrganizer(it, userEmail) }.forEach { eventsList.add(it) }

        val bookingList = mutableListOf<Booking>()
        eventsList.forEach {
            bookingList.add(it.toBookingModel())
        }
        return bookingList;
    }

    private fun checkEventOrganizer(event: Event, email: String): Boolean {
        if (event.organizer?.email?.equals(defaultCalendar) ?: false) {
            return event.description.contains(email)
        }
        return event.organizer?.email?.equals(email) ?: false
    }

    private fun findUserEmailByUserId(id: UUID): String {
        return userRepository.findById(id)?.email ?: throw InstanceNotFoundException(
            UserEntity::class, "User with id $id not found"
        )
    }

    override fun findAllByWorkspaceId(workspaceId: UUID): List<Booking> {
        val bookingList: MutableList<Booking> = mutableListOf()
        findAllEntitiesByWorkspaceId(workspaceId).forEach { bookingList.add(it.toBookingModel()) }
        return bookingList
    }

    private fun findAllEntitiesByWorkspaceId(workspaceId: UUID): List<Event> {
        val calendarId = getCalendarIdByWorkspace(workspaceId)
        return findAllEntities().filter { hasId(it, calendarId) }
    }

    private fun hasId(event: Event, calendarId: String): Boolean {
        event.attendees.forEach {
            if (it.email == calendarId) return true
        }
        return false
    }

    override fun findAllByOwnerAndWorkspaceId(ownerId: UUID, workspaceId: UUID): List<Booking> {
        return findAllByOwnerId(ownerId).filter {
            it.workspace.id == workspaceId
        }
    }

    override fun findAll(): List<Booking> {
        val bookingList = mutableListOf<Booking>()
        findAllEntities().forEach { bookingList.add(it.toBookingModel()) }
        return bookingList
    }

    override fun save(booking: Booking): Booking {
        val event = booking.toGoogleEvent()
        val savedEvent = calendar.Events().insert(defaultCalendar, event).execute()
        return savedEvent.toBookingModel()//findById(savedEvent.id) ?: throw Exception("Calendar save goes wrong")
    }

    private fun findAllEntities(): List<Event> {
        return calendarEvents.list(defaultCalendar).execute().items.filter { event ->
            event.status != "cancelled"
        }.filter { (it?.start?.dateTime?.value ?: 0) > minTime }
    }

    override fun update(booking: Booking): Booking {
        deleteById(booking.id ?: throw MissingIdException("Update model must have id"))
        return save(booking)

//        val event = booking.toGoogleEvent()
//        booking.workspace.id
//            ?: throw MissingIdException("Workspace ${booking.workspace.name} must have id to push event ${event.id}")
//        val id = getCalendarIdByWorkspace(booking.workspace.id!!)
//        calendarEvents.update(id, event.id, event)
//
//        return findById(booking.id ?: throw MissingIdException("Booking must have id to update event"))
//            ?: throw InstanceNotFoundException(
//                Booking::class, "Cannot to found booking directly after booking", null
//            )
    }
}