package office.effective.features.booking.repository

import com.google.api.client.googleapis.json.GoogleJsonResponseException
import com.google.api.client.util.DateTime
import com.google.api.services.calendar.Calendar
import com.google.api.services.calendar.model.Event
import office.effective.common.exception.InstanceNotFoundException
import office.effective.common.exception.MissingIdException
import office.effective.common.exception.WorkspaceUnavailableException
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
    private val defaultTimeZone = config.propertyOrNull("calendar.timeZone")?.getString()?: throw Exception("Config file does not contain default timeZone value")

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
     * @author Danil Kiselev, Daniil Zavyalov
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
     * @author Danil Kiselev, Daniil Zavyalov
     */
    private fun hasAttendee(event: Event, attendeeEmail: String): Boolean {
        event.attendees?.forEach {
            if (it.email == attendeeEmail) return true
        }
        return false
    }

    /**
     * Returns all bookings with the given workspace id
     *
     * @param workspaceId
     * @param eventRangeFrom lower bound (exclusive) for a endBooking to filter by.
     * Old Google calendar events may not appear correctly in the system and cause unexpected exceptions
     * @param eventRangeTo upper bound (exclusive) for a beginBooking to filter by. Optional.
     * @return List of all workspace [Booking]
     * @author Daniil Zavyalov, Danil Kiselev
     */
    override fun findAllByWorkspaceId(workspaceId: UUID, eventRangeFrom: Long, eventRangeTo: Long?): List<Booking> {
        val workspaceCalendarId = getCalendarIdByWorkspace(workspaceId)
        val eventsWithWorkspace = basicQuery(eventRangeFrom, eventRangeTo)
            .setQ(workspaceCalendarId)
            .execute().items

        val result: MutableList<Booking> = mutableListOf()
        eventsWithWorkspace?.forEach { event ->
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
     * @author Danil Kiselev, Daniil Zavyalov
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
     * @param eventRangeFrom lower bound (exclusive) for a endBooking to filter by.
     * Old Google calendar events may not appear correctly in the system and cause unexpected exceptions
     * @param eventRangeTo upper bound (exclusive) for a beginBooking to filter by. Optional.
     * @return List of all user [Booking]
     * @throws InstanceNotFoundException if user with the given id doesn't exist in database
     * @author Daniil Zavyalov, Danil Kiselev
     */
    override fun findAllByOwnerId(ownerId: UUID, eventRangeFrom: Long, eventRangeTo: Long?): List<Booking> {
        val userEmail: String = findUserEmailByUserId(ownerId)

        val eventsWithUser = basicQuery(eventRangeFrom, eventRangeTo)
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
     * @param eventRangeFrom lower bound (exclusive) for a endBooking to filter by.
     * Old Google calendar events may not appear correctly in the system and cause unexpected exceptions
     * @param eventRangeTo upper bound (exclusive) for a beginBooking to filter by. Optional.
     * @return List of all [Booking]s with the given workspace and owner id
     * @author Daniil Zavyalov
     */
    override fun findAllByOwnerAndWorkspaceId(
        ownerId: UUID,
        workspaceId: UUID,
        eventRangeFrom: Long,
        eventRangeTo: Long?
    ): List<Booking> {
        val userEmail: String = findUserEmailByUserId(ownerId)
        val workspaceCalendarId = getCalendarIdByWorkspace(workspaceId)

        val eventsWithUserAndWorkspace = basicQuery(eventRangeFrom, eventRangeTo)
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
     * @param eventRangeFrom lower bound (exclusive) for a endBooking to filter by.
     * Old Google calendar events may not appear correctly in the system and cause unexpected exceptions
     * @param eventRangeTo upper bound (exclusive) for a beginBooking to filter by. Optional.
     * @return All [Booking]s
     * @author Daniil Zavyalov
     */
    override fun findAll(eventRangeFrom: Long, eventRangeTo: Long?): List<Booking> {
        return basicQuery(eventRangeFrom, eventRangeTo).execute().items.map { event ->
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
        val workspaceCalendarId = calendarIdsRepository.findByWorkspace(
            booking.workspace.id ?: throw MissingIdException("On update save you must have workspace id")
        )
        val event = googleCalendarConverter.toGoogleEvent(booking)
        if (checkBookingAvailable(
                event, workspaceCalendarId
            )
        ) {
            val savedEvent = calendar.Events().insert(defaultCalendar, event).execute()
            return googleCalendarConverter.toBookingModel(savedEvent)
        } else {
            throw WorkspaceUnavailableException("Booking unavailable. Workspace ${booking.workspace.name} (id = ${booking.workspace.id}) has already booked by someone")
        }

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
        val workspaceCalendarId = calendarIdsRepository.findByWorkspace(
            booking.workspace.id ?: throw MissingIdException("On update save you must have workspace id")
        )
        val bookingId = booking.id ?: throw MissingIdException("Update model must have id")
        if (!existsById(bookingId)) {
            throw InstanceNotFoundException(WorkspaceBookingEntity::class, "Booking with id $bookingId not wound")
        }
        if (checkBookingAvailable(
                googleCalendarConverter.toGoogleEvent(booking), workspaceCalendarId
            )
        ) {
            deleteById(bookingId)
            return save(booking)
        } else {
            throw WorkspaceUnavailableException("Booking unavailable. Workspace ${booking.workspace.name} (id = ${booking.workspace.id}) has already booked by someone")
        }
    }

    /**
     * Launch checkSingleEventCollision for non-cicle event or receive instances for recurrent event and checks all of them
     *
     * @param incomingEvent: [Event] - in case of needs it will be inserted into calendar, checked, and, after this, deleted.
     * @author Kiselev Danil
     * */
    private fun checkBookingAvailable(incomingEvent: Event, workspaceCalendarId: String): Boolean {
        var isAvailable: Boolean = false;
        incomingEvent.start.setTimeZone(defaultTimeZone)
        incomingEvent.end.setTimeZone(defaultTimeZone)
        val eventOnCheck = calendarEvents.insert(defaultCalendar, incomingEvent).execute()

        if (incomingEvent.recurrence != null) {
            //TODO: Check, if we can receive instances without pushing this event into calendar
            calendarEvents.instances(defaultCalendar, eventOnCheck.id).execute().items.forEach { event ->
                if (!checkSingleEventCollision(event, workspaceCalendarId)) {
                    calendarEvents.delete(defaultCalendar, eventOnCheck.id).execute()
                    return false
                } else {
                    isAvailable = true
                }
            }
        } else {
            isAvailable = checkSingleEventCollision(eventOnCheck, workspaceCalendarId)
        }
        calendarEvents.delete(defaultCalendar, eventOnCheck.id).execute()
        return isAvailable
    }

    private fun checkSingleEventCollision(event: Event, workspaceCalendarId: String): Boolean {
        val leftBorder = event.start.dateTime.value - 1
        val rightBorder = event.start.dateTime.value + 1
        val savedEvents = basicQuery(leftBorder, rightBorder).execute().items
        for (i in savedEvents) {
            if (!((i.start.dateTime.value > event.end.dateTime.value) || (i.end.dateTime.value < event.start.dateTime.value)) && (i.id != event.id)) {
                return false
            }
        }

        return true
    }
}