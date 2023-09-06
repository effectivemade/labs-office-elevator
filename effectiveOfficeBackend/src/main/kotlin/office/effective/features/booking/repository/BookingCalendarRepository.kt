package office.effective.features.booking.repository

import com.google.api.client.googleapis.json.GoogleJsonResponseException
import com.google.api.client.util.DateTime
import com.google.api.services.calendar.Calendar
import com.google.api.services.calendar.model.Event
import office.effective.common.exception.InstanceNotFoundException
import office.effective.common.exception.MissingIdException
import office.effective.config
import office.effective.features.calendar.repository.CalendarIdsRepository
import office.effective.features.booking.converters.GoogleCalendarConverter
import office.effective.features.user.repository.UserRepository
import office.effective.model.Booking
import office.effective.features.user.repository.UserEntity
import office.effective.features.workspace.repository.WorkspaceRepository
import java.util.*

/**
 * Class that executes Google calendar queries
 *
 * Filters out all events that have a start less than the calendar.minTime from application.conf
 */
class BookingCalendarRepository(
    private val calendarIdsRepository: CalendarIdsRepository,
    private val userRepository: UserRepository,
    private val calendar: Calendar,
    private val workspaceRepository: WorkspaceRepository,
    private val googleCalendarConverter: GoogleCalendarConverter
) : IBookingRepository {
    private val calendarEvents = calendar.Events()
    private val defaultCalendar = config.propertyOrNull("auth.app.defaultAppEmail")?.getString()
        ?: throw Exception("Config file does not contain default gmail value")
    private val minTime = config.propertyOrNull("calendar.minTime")?.getString()?.toLong()
        ?: throw Exception("Config file does not contain minimum time")

    /**
     * Finds workspace calendar id by workspace id
     *
     * @param workspaceId
     * @return calendar id by workspace id in database
     * @author Danil Kiselev, Daniil Zavyalov
     */
    private fun getCalendarIdByWorkspace(workspaceId: UUID): String {
        return try {
            calendarIdsRepository.findByWorkspace(workspaceId)
        } catch (e: InstanceNotFoundException) {
            defaultCalendar
        }
    }

    /**
     * Returns whether a booking with the given id exists
     *
     * @param id booking id
     * @return true if booking exists
     * @author Danil Kiselev
     */
    override fun existsById(id: String): Boolean {
        val event: Any?
        event = findByCalendarIdAndBookingId(defaultCalendar, id)
        return event != null
    }

    /**
     * Deletes the booking with the given id
     *
     * @param id booking id
     * @author Danil Kiselev
     */
    override fun deleteById(id: String) {
        calendarEvents.delete(defaultCalendar, id).execute() //We can't delete from workspace calendar
    }

    /**
     * Retrieves a booking model by its id.
     * Retrieved booking contains user and workspace models without integrations and utilities
     *
     * @param bookingId booking id
     * @return [Booking] with the given [bookingId] or null if booking with the given id doesn't exist
     * @author Daniil Zavyalov, Danil Kiselev
     */
    override fun findById(bookingId: String): Booking? {
        val event: Event? = findByCalendarIdAndBookingId(defaultCalendar, bookingId)
        return event?.let { googleCalendarConverter.toBookingModel(it) }
    }

    /**
     * Retrieves an event by calendar id and booking id.
     * Retrieved booking contains user and workspace models without integrations and utilities
     *
     * @param bookingId booking id
     * @param calendarId the calendar in which to search for the event
     * @return [Event] with the given [bookingId] from calendar with [calendarId]
     * or null if event with the given id doesn't exist
     * @author Danil Kiselev
     */
    private fun findByCalendarIdAndBookingId(calendarId: String, bookingId: String): Event? {
        return try {
            calendar.events().get(calendarId, bookingId).execute()
        } catch (e: GoogleJsonResponseException) {
            if(e.statusCode == 404) return null
            else throw e
        }
    }

    /**
     * Returns all bookings with the given owner id
     *
     * @param ownerId
     * @return List of all user [Booking]
     * @throws InstanceNotFoundException if user with the given id doesn't exist in database
     * @author Danil Kiselev
     */
    override fun findAllByOwnerId(ownerId: UUID): List<Booking> {
        val userEmail: String = findUserEmailByUserId(ownerId)
        val eventsList = mutableListOf<Event>()
        findAllEntities().filter { checkEventOrganizer(it, userEmail) }.forEach { eventsList.add(it) }
        //TODO: query without retrieving all events
        val bookingList = mutableListOf<Booking>()
        eventsList.forEach {
            bookingList.add(googleCalendarConverter.toBookingModel(it))
        }
        return bookingList;
    }

    /**
     * Checks whether the given event organised by user with the given email.
     * Organiser email may be specified at [Event.description] or [Event.organizer].email
     *
     * @param event
     * @param email
     * @return List of all user [Booking]
     * @author Danil Kiselev
     */
    private fun checkEventOrganizer(event: Event, email: String): Boolean {
        if (event.organizer?.email?.equals(defaultCalendar) ?: false) { //Don't replace '?: false' with '== true'
            return event.description.contains(email)
        }
        return event.organizer?.email?.equals(email) ?: false
    }

    /**
     * Retrieves user email from database by user id
     *
     * @param id
     * @return user email
     * @throws InstanceNotFoundException if user with the given id doesn't exist in database
     * @author Danil Kiselev
     */
    private fun findUserEmailByUserId(id: UUID): String {
        return userRepository.findById(id)?.email ?: throw InstanceNotFoundException(
            UserEntity::class, "User with id $id not found"
        )
    }

    /**
     * Returns all bookings with the given workspace id
     *
     * @param workspaceId
     * @return List of all workspace [Booking]
     * @author Danil Kiselev
     */
    override fun findAllByWorkspaceId(workspaceId: UUID): List<Booking> {
        val bookingList: MutableList<Booking> = mutableListOf()
        findAllEntitiesByWorkspaceId(workspaceId).forEach { bookingList.add(googleCalendarConverter.toBookingModel(it)) }
        return bookingList
    }

    /**
     * Returns all events for workspace with the given id
     *
     * @param workspaceId
     * @return List of all workspace [Event]s
     * @author Danil Kiselev
     */
    private fun findAllEntitiesByWorkspaceId(workspaceId: UUID): List<Event> {
        val calendarId = getCalendarIdByWorkspace(workspaceId)
        return findAllEntities().filter { hasId(it, calendarId) }
    }

    /**
     * Checks whether the event contains workspace with the given calendar id
     * (Rooms (workspaces) are also attendees of event).
     *
     * @param event
     * @param calendarId
     * @return List of all workspace [Event]s
     * @author Danil Kiselev
     */
    private fun hasId(event: Event, calendarId: String): Boolean {
        event.attendees.forEach {
            if (it.email == calendarId) return true
        }
        return false
    }

    /**
     * Returns all bookings with the given workspace and owner id
     *
     * @param ownerId
     * @param workspaceId
     * @return List of all [Booking]s with the given workspace and owner id
     * @author anil Kiselev
     */
    override fun findAllByOwnerAndWorkspaceId(ownerId: UUID, workspaceId: UUID): List<Booking> {
        return findAllByOwnerId(ownerId).filter {
            it.workspace.id == workspaceId
        }
    }

    /**
     * Retrieves all bookings
     *
     * @return All [Booking]s
     * @author Danil Kiselev
     */
    override fun findAll(): List<Booking> {
        val bookingList = mutableListOf<Booking>()
        findAllEntities().forEach { bookingList.add(googleCalendarConverter.toBookingModel(it)) }
        return bookingList
    }

    /**
     * Saves a given booking. If given model will have an id, it will be ignored.
     * Use the returned model for further operations
     *
     * @param booking [Booking] to be saved
     * @return saved [Booking]
     * @author Danil Kiselev
     */
    override fun save(booking: Booking): Booking {
        //TODO: add throwing WorkspaceUnavailableException if workspace can't be booked in a given period
        val event = googleCalendarConverter.toGoogleEvent(booking)
        val savedEvent = calendar.Events().insert(defaultCalendar, event).execute()
        return googleCalendarConverter.toBookingModel(savedEvent)//findById(savedEvent.id) ?: throw Exception("Calendar save goes wrong")
    }

    /**
     * Retrieves all [Event].
     *
     * Filters out all events that have a start less than the calendar.minTime from application.conf
     *
     * @return All [Booking]s
     * @author Daniil Zavyalov
     */
    private fun findAllEntities(): List<Event> {
        return calendarEvents.list(defaultCalendar).setTimeMin(DateTime(minTime)).execute().items.filter { event ->
            event.status != "cancelled"
        }.filter { (it?.start?.dateTime?.value ?: 0) > minTime }
    }

    /**
     * Updates a given booking. Use the returned model for further operations
     *
     * @param booking changed booking
     * @return [Booking] after change saving
     * @throws MissingIdException if [Booking.id] is null
     * @throws InstanceNotFoundException if booking given id doesn't exist in the database
     * @author Daniil Zavyalov, Danil Kiselev
     */
    override fun update(booking: Booking): Booking {
        val bookingId = booking.id ?: throw MissingIdException("Update model must have id")
        if (!existsById(bookingId)) {
            throw InstanceNotFoundException(WorkspaceBookingEntity::class, "Booking with id $bookingId not wound")
        }
        deleteById(bookingId)
        return save(booking)
        //TODO: add throwing WorkspaceUnavailableException if workspace can't be booked in a given period
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


    private fun checkBookingAvailable(incomingEvent: Event): Boolean {
        if (incomingEvent.recurrence != null) {
            val events = calendarEvents.instances(defaultCalendar, incomingEvent.id).execute()
            events.forEach { event ->
                checkSingleEventCollision(event.value as Event)
            }
        } else {
            checkSingleEventCollision(incomingEvent)
        }
        return false
    }

    private fun checkSingleEventCollision(event: Event): Boolean {
        var flag: Boolean = true
        val workspaceId = googleCalendarConverter.toBookingModel(event).workspace.id
            ?: throw Exception("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
        val savedEvents = findAllByWorkspaceId(workspaceId)
        for (okEvent in savedEvents) {

            /*
            Условия не-букинга [] - saved events, () - new event
            1) ---[]---(--[--)---]-----[]-------------------------
            2) ---[]-----[---(--]--)---[]-------------------------
            3) ---[]-----[--(--)-]-----[]-------------------------
            4.1) ---[]----(-[-----]--)---[]-----------------------
            4.2) ---[]---(--[-----]-----[----]---)----------------
            */

            if (TODO("Check events collision")) {
                flag = false
            }
        }
        return flag
    }
}