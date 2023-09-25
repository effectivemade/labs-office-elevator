package office.effective.features.booking.repository

import com.google.api.client.googleapis.json.GoogleJsonResponseException
import com.google.api.client.util.DateTime
import com.google.api.services.calendar.Calendar
import com.google.api.services.calendar.model.Event
import office.effective.common.exception.InstanceNotFoundException
import office.effective.common.exception.MissingIdException
import office.effective.common.exception.WorkspaceUnavailableException
import office.effective.config
import office.effective.features.booking.converters.GoogleCalendarConverter
import office.effective.features.user.repository.UserRepository
import office.effective.features.workspace.repository.WorkspaceEntity
import office.effective.features.workspace.repository.WorkspaceRepository
import office.effective.model.Booking
import java.util.*

/**
 * Class that executes Google calendar queries for booking workspaces
 *
 * Filters out all events that have a start less than the calendar.minTime from application.conf
 */
class BookingWorkspaceRepository(
    private val calendar: Calendar,
    private val googleCalendarConverter: GoogleCalendarConverter,
    private val workspaceRepository: WorkspaceRepository,
    private val userRepository: UserRepository
) : IBookingRepository {
    private val calendarEvents = calendar.Events()
    private val workspaceCalendar = config.propertyOrNull("calendar.workspaceCalendar")?.getString()
        ?: throw Exception("Config file does not contain workspace Google calendar id")

    /**
     * Returns whether a booking with the given id exists
     *
     * @param id booking id
     * @return true if booking exists
     * @author Daniil Zavyalov
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
     * @author Daniil Zavyalov
     */
    override fun deleteById(id: String) {
        try {
            calendarEvents.delete(workspaceCalendar, id).execute()
        } catch (e: GoogleJsonResponseException) {
            if (e.statusCode != 404) {
                throw e
            }
        }
    }

    /**
     * Retrieves a booking model by its id.
     * Retrieved booking contains user and workspace models without integrations and utilities
     *
     * @param bookingId booking id
     * @return [Booking] with the given [bookingId] or null if booking with the given id doesn't exist
     * @author Daniil Zavyalov
     */
    override fun findById(bookingId: String): Booking? {
        val event: Event? = findByCalendarIdAndBookingId(bookingId)
        return event?.let { googleCalendarConverter.toWorkspaceBooking(it) }
    }

    /**
     * Retrieves an event by calendar id and booking id.
     * Retrieved booking contains user and workspace models without integrations and utilities
     *
     * @param bookingId booking id
     * @param calendarId the calendar in which to search for the event
     * @return [Event] with the given [bookingId] from calendar with [calendarId]
     * or null if event with the given id doesn't exist
     * @author Daniil Zavyalov
     */
    private fun findByCalendarIdAndBookingId(bookingId: String, calendarId: String = workspaceCalendar): Event? {
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
        calendarId: String = workspaceCalendar
    ): Calendar.Events.List {
        return calendarEvents.list(calendarId)
            .setSingleEvents(singleEvents)
            .setTimeMin(DateTime(timeMin))
            .setTimeMax(timeMax?.let { DateTime(it) })
    }

    /**
     * Returns all bookings with the given workspace id
     *
     * @param workspaceId
     * @param eventRangeFrom lower bound (exclusive) for a endBooking to filter by.
     * Old Google calendar events may not appear correctly in the system and cause unexpected exceptions
     * @param eventRangeTo upper bound (exclusive) for a beginBooking to filter by. Optional.
     * @return List of all workspace [Booking]
     * @author Daniil Zavyalov
     */
    override fun findAllByWorkspaceId(workspaceId: UUID, eventRangeFrom: Long, eventRangeTo: Long?): List<Booking> {
        val eventsWithWorkspace = basicQuery(eventRangeFrom, eventRangeTo)
            .setQ(workspaceId.toString())
            .execute().items

        return eventsWithWorkspace.map { event ->
            googleCalendarConverter.toWorkspaceBooking(event)
        }
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
     * @author Daniil Zavyalov
     */
    override fun findAllByOwnerId(ownerId: UUID, eventRangeFrom: Long, eventRangeTo: Long?): List<Booking> {
        val eventsWithUser = basicQuery(eventRangeFrom, eventRangeTo)
            .setQ(ownerId.toString())
            .execute().items

        return eventsWithUser.map { event ->
            googleCalendarConverter.toWorkspaceBooking(event)
        }
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
        val eventsWithUserAndWorkspace = basicQuery(eventRangeFrom, eventRangeTo)
            .setQ("$workspaceId $ownerId")
            .execute().items

        return eventsWithUserAndWorkspace.map { event ->
            googleCalendarConverter.toWorkspaceBooking(event)
        }
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
            googleCalendarConverter.toWorkspaceBooking(event)
        }
    }

    /**
     * Saves a given booking. If given model will have an id, it will be ignored.
     * Use the returned model for further operations
     *
     * @param booking [Booking] to be saved
     * @return saved [Booking]
     * @author Daniil Zavyalov, Danil Kiselev
     */
    override fun save(booking: Booking): Booking {
        val workspaceId = booking.workspace.id ?: throw MissingIdException("Missing booking workspace id")
        val userId = booking.owner.id ?: throw MissingIdException("Missing booking owner id")
        if (!userRepository.existsById(userId)) {
            throw InstanceNotFoundException(
                WorkspaceEntity::class, "User with id $workspaceId not wound"
            )
        }
        val event = googleCalendarConverter.toGoogleWorkspaceEvent(booking)

        val savedEvent = calendar.Events().insert(workspaceCalendar, event).execute()

        if (checkBookingAvailable(savedEvent, workspaceId)) {
            return googleCalendarConverter.toWorkspaceBooking(savedEvent)
        } else {
            deleteById(savedEvent.id)
            throw WorkspaceUnavailableException("Workspace ${booking.workspace.name} " +
                    "unavailable at time between ${booking.beginBooking} and ${booking.endBooking}")
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
        val workspaceId = booking.workspace.id ?: throw MissingIdException("Missing booking workspace id")
        val bookingId = booking.id ?: throw MissingIdException("Update model must have id")
        val previousVersionOfEvent = findByCalendarIdAndBookingId(bookingId) ?: throw InstanceNotFoundException(
            WorkspaceBookingEntity::class, "Booking with id $bookingId not wound"
        )
        val eventOnUpdate = googleCalendarConverter.toGoogleWorkspaceEvent(booking)

        val updatedEvent: Event = calendarEvents.update(workspaceCalendar, bookingId, eventOnUpdate).execute()

        val sequence = updatedEvent.sequence
        if (checkBookingAvailable(updatedEvent, workspaceId)) {
            return googleCalendarConverter.toWorkspaceBooking(updatedEvent)
        } else {
            previousVersionOfEvent.sequence = sequence
            calendarEvents.update(workspaceCalendar, bookingId, previousVersionOfEvent).execute()
            throw WorkspaceUnavailableException("Workspace ${booking.workspace.name} " +
                    "unavailable at time between ${booking.beginBooking} and ${booking.endBooking}")
        }
    }

    /**
     * Launch checkSingleEventCollision for non-cycle event or
     * receive instances for recurrent event and checks all instances.
     *
     * @param incomingEvent: [Event] - Must take only SAVED event
     * @return Boolean. True if booking available
     * @author Kiselev Danil, Daniil Zavyalov
     * */
    private fun checkBookingAvailable(incomingEvent: Event, workspaceId: UUID): Boolean {
        var isAvailable = false;

        if (incomingEvent.recurrence != null) {
            //TODO: Check, if we can receive instances without pushing this event into calendar
            val instances = calendarEvents.instances(workspaceCalendar, incomingEvent.id).execute().items
            for (instance in instances) {
                if (!checkSingleEventCollision(instance, workspaceId)) {
                    return false
                } else {
                    isAvailable = true
                }
            }
        } else {
            isAvailable = checkSingleEventCollision(incomingEvent, workspaceId)
        }
        return isAvailable
    }

    /**
     * Contains collision condition. Checks collision between single event from param
     * and all saved events from [Event.start] until [Event.end]
     *
     * @param event: [Event] - Must take only SAVED event
     * @author Kiselev Danil, Daniil Zavyalov
     * */
    private fun checkSingleEventCollision(event: Event, workspaceId: UUID): Boolean {
        val events = basicQuery(event.start.dateTime.value, event.end.dateTime.value)
            .setQ(workspaceId.toString())
            .execute().items
        for (i in events) {
            if (
                !((i.start.dateTime.value >= event.end.dateTime.value) || (i.end.dateTime.value <= event.start.dateTime.value))
                && (i.id != event.id)
            ) {
                return false
            }
        }
        return true
    }
}
