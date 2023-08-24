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
import office.effective.features.workspace.repository.WorkspaceRepository
import org.ktorm.dsl.eq
import java.util.*

class BookingCalendarRepository(
    private val calendarRepository: CalendarRepository,
    private val userRepository: UserRepository,
    private val calendar: Calendar,
    private val workspaceRepository: WorkspaceRepository
) : IBookingRepository {
    private val calendarEvents = calendar.Events();

    private fun getCalendarIdByWorkspace(workspaceId: UUID): String {
        if (workspaceRepository.findById(workspaceId)?.tag != "meeting") {
            return config.propertyOrNull("auth.app.defaultAppEmail")?.getString() ?: throw Exception(
                "Config file does not contain default gmail value"
            )
        }
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
            val a = findByCalendarIdAndBookingId(it, bookingId)
            if (a != null) {
                event = a
            }
        }
        return event?.toBookingModel()
    }

    private fun findByCalendarIdAndBookingId(calendarId: String, bookingId: String): Event? {
        return calendarEvents.list(calendarId).execute().items.find { it.id.equals(bookingId) }
    }

    override fun findAllByOwnerId(ownerId: UUID): List<Booking> {
        val userEmail: String = findUserEmailByUserId(ownerId)
        val eventsList = mutableListOf<Event>()
        for (calendarId in calendarRepository.findAllCalendarsId()) {
            calendarEvents.list(calendarId).execute().items.filter {
                it.status != "cancelled"
            }.filter {
                it?.organizer?.email?.equals(
                    userEmail
                ) ?: false
            }.forEach { eventsList.add(it) }
        }
//        calendarRepository.findAllCalendarsId().forEach {
//            eventsList.addAll(calendarEvents.list(it).execute().items.filter { it.organizer.email.equals(userEmail) }
//                .toList())
//        }
        val bookingList = mutableListOf<Booking>()
        eventsList.forEach { bookingList.add(it.toBookingModel()) }
        return bookingList;
    }

    private fun findUserEmailByUserId(id: UUID): String {
        return userRepository.findById(id).email
    }

    override fun findAllByWorkspaceId(workspaceId: UUID): List<Booking> {
        val bookingList: MutableList<Booking> = mutableListOf()
        findAllEntitiesByWorkspaceId(workspaceId).forEach { bookingList.add(it.toBookingModel()) }
        return bookingList
    }

    private fun findAllEntitiesByWorkspaceId(workspaceId: UUID): List<Event> {
        val calendarId = getCalendarIdByWorkspace(workspaceId)
        return calendarEvents.list(calendarId).execute().items.filter {
            it.status != "cancelled"
        }.filter { (it?.start?.dateTime?.value ?: 0) > GregorianCalendar().time.time }
    }

    override fun findAllByOwnerAndWorkspaceId(ownerId: UUID, workspaceId: UUID): List<Booking> {
        val email = findUserEmailByUserId(ownerId)
        return findAllByOwnerId(ownerId).filter {
            it.workspace.id == workspaceId
        }
//        return findAllByWorkspaceId(workspaceId).filter {
//            it.owner.email.equals(
//                email
//            )
//        }
    }

    override fun findAll(): List<Booking> {
        val bookingList = mutableListOf<Booking>()
        calendarRepository.findAllCalendarsId().forEach { calendarId ->
            calendarEvents.list(calendarId).execute().items.filter { event ->
                event.status != "cancelled"
            }.filter { (it?.start?.dateTime?.value ?: 0) > GregorianCalendar().time.time }
                .forEach { bookingList.add(it.toBookingModel()) }
        }
        return bookingList
    }

    override fun save(booking: Booking): Booking {

        val event = booking.toGoogleEvent()
        val calendarID: String =
            calendarRepository.findByWorkspace(booking.workspace.id ?: throw MissingIdException("workspace model"))
        val savedEvent = calendar.Events().insert("effective.office@effective.band", event).execute()
        println("=====================================")
        println(savedEvent.id)
        println("=====================================")
        return savedEvent.toBookingModel()//findById(savedEvent.id) ?: throw Exception("Calendar save goes wrong")
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