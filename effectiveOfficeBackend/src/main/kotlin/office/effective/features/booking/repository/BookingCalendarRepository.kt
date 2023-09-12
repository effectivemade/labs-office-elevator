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
    private val googleCalendarConverter: GoogleCalendarConverter
) : IBookingRepository {
    private val calendarEvents = calendar.Events()
    private val defaultCalendar = config.propertyOrNull("auth.app.defaultAppEmail")?.getString()
        ?: throw Exception("Config file does not contain default gmail value")

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
        event = findByCalendarIdAndBookingId(id)
        return event != null
    }

    /**
     * Deletes the booking with the given id
     *
     * @param id booking id
     * @author Danil Kiselev
     */
    override fun deleteById(id: String) {
        calendarEvents.delete(defaultCalendar, id).execute() //We can't delete directly from workspace calendar
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
        val event: Event? = findByCalendarIdAndBookingId(bookingId)
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
    private fun findByCalendarIdAndBookingId(bookingId: String, calendarId: String = defaultCalendar): Event? {
        return try {
            calendar.events().get(calendarId, bookingId).execute()
        } catch (e: GoogleJsonResponseException) {
            if (e.statusCode == 404) return null
            else throw e
        }
    }

    /**
     * Request template containing all required parameters
     *
     * @param timeMin lover bound for filtering bookings by start time.
     * Old Google calendar events may not appear correctly in the system and cause unexpected exceptions
     * @param timeMax
     * @param singleEvents
     * @param calendarId
     * @author Daniil Zavyalov
     */
    private fun basicQuery(
        timeMin: Long,
        timeMax: Long? = null,
        singleEvents: Boolean = true,
        calendarId: String = defaultCalendar
    ): Calendar.Events.List {
        return calendarEvents.list(calendarId)
            .setSingleEvents(singleEvents)
            .setTimeMin(DateTime(timeMin))
            .setTimeMax(timeMax?.let { DateTime(it) })
            .setMaxResults(2500)
    }

    /**
     * Checks whether the event contains workspace with the given calendar id
     * (Rooms (workspaces) are also attendees of event).
     *
     * @param event
     * @param attendeeEmail
     * @return List of all workspace [Event]s
     * @author Danil Kiselev
     */
    private fun hasAttendee(event: Event, attendeeEmail: String): Boolean {
        event.attendees.forEach {
            if (it.email == attendeeEmail) return true
        }
        return false
    }

    /**
     * Returns all bookings with the given workspace id
     *
     * @param workspaceId
     * @param minStartTime lover bound for filtering bookings by start time.
     * Old Google calendar events may not appear correctly in the system and cause unexpected exceptions
     * @param maxStartTime use to set an upper bound for filtering bookings by start time
     * @return List of all workspace [Booking]
     * @author Danil Kiselev
     */
    override fun findAllByWorkspaceId(workspaceId: UUID, minStartTime: Long, maxStartTime: Long?): List<Booking> {
        val workspaceCalendarId = getCalendarIdByWorkspace(workspaceId)
        val eventsWithWorkspace = basicQuery(minStartTime, maxStartTime)
            .setQ(workspaceCalendarId)
            .execute().items

        val result: MutableList<Booking> = mutableListOf()
        eventsWithWorkspace.forEach { event ->
            if (hasAttendee(event, workspaceCalendarId)) {
                result.add(googleCalendarConverter.toBookingModel(event))
            }
        }
        return result
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
        if (event.organizer?.email == defaultCalendar) {
            return event.description.contains(email)
        }
        //TODO: if event was created by defaultCalendar account, but not from Effective Office, this method will return false
        return event.organizer?.email == email
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
     * Returns all bookings with the given owner id
     *
     * @param ownerId
     * @param minStartTime lover bound for filtering bookings by start time.
     * Old Google calendar events may not appear correctly in the system and cause unexpected exceptions
     * @param maxStartTime use to set an upper bound for filtering bookings by start time
     * @return List of all user [Booking]
     * @throws InstanceNotFoundException if user with the given id doesn't exist in database
     * @author Danil Kiselev
     */
    override fun findAllByOwnerId(ownerId: UUID, minStartTime: Long, maxStartTime: Long?): List<Booking> {
        val userEmail: String = findUserEmailByUserId(ownerId)

        val eventsWithUser = basicQuery(minStartTime, maxStartTime)
            .setQ(userEmail)
            .execute().items

        val result = mutableListOf<Booking>()
        eventsWithUser.forEach { event ->
            if (checkEventOrganizer(event, userEmail)) {
                result.add(googleCalendarConverter.toBookingModel(event))
            }
        }

        return result
    }

    /**
     * Returns all bookings with the given workspace and owner id
     *
     * @param ownerId
     * @param workspaceId
     * @param minStartTime lover bound for filtering bookings by start time.
     * Old Google calendar events may not appear correctly in the system and cause unexpected exceptions
     * @param maxStartTime use to set an upper bound for filtering bookings by start time
     * @return List of all [Booking]s with the given workspace and owner id
     * @author anil Kiselev
     */
    override fun findAllByOwnerAndWorkspaceId(
        ownerId: UUID,
        workspaceId: UUID,
        minStartTime: Long,
        maxStartTime: Long?
    ): List<Booking> {
        val userEmail: String = findUserEmailByUserId(ownerId)
        val workspaceCalendarId = getCalendarIdByWorkspace(workspaceId)

        val eventsWithUserAndWorkspace = basicQuery(minStartTime, maxStartTime)
            .setQ("$userEmail $workspaceCalendarId")
            .execute().items

        val result = mutableListOf<Booking>()
        eventsWithUserAndWorkspace.forEach { event ->
            if (checkEventOrganizer(event, userEmail) && hasAttendee(event, workspaceCalendarId)) {
                result.add(googleCalendarConverter.toBookingModel(event))
            }
        }

        return result
    }

    /**
     * Retrieves all bookings
     *
     * @param minStartTime lover bound for filtering bookings by start time.
     * Old Google calendar events may not appear correctly in the system and cause unexpected exceptions
     * @param maxStartTime use to set an upper bound for filtering bookings by start time
     * @return All [Booking]s
     * @author Danil Kiselev
     */
    override fun findAll(minStartTime: Long, maxStartTime: Long?): List<Booking> {
        return basicQuery(minStartTime, maxStartTime).execute().items.map { event ->
            googleCalendarConverter.toBookingModel(event)
        }
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
}