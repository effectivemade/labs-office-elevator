package office.effective.features.booking.converters

import com.google.api.client.util.DateTime
import com.google.api.services.calendar.model.Event
import com.google.api.services.calendar.model.Event.Organizer
import com.google.api.services.calendar.model.EventAttendee
import com.google.api.services.calendar.model.EventDateTime
import model.Recurrence.Companion.toRecurrence
import office.effective.common.utils.UuidValidator
import office.effective.config
import office.effective.dto.BookingDTO
import office.effective.dto.UserDTO
import office.effective.dto.WorkspaceDTO
import office.effective.features.calendar.repository.CalendarIdsRepository
import office.effective.features.user.converters.UserDTOModelConverter
import office.effective.features.user.repository.UserRepository
import office.effective.features.workspace.converters.WorkspaceFacadeConverter
import office.effective.model.Booking
import office.effective.model.UserModel
import office.effective.model.Workspace
import office.effective.features.booking.converters.RecurrenceRuleFactory.getRecurrence
import office.effective.features.booking.converters.RecurrenceRuleFactory.rule
import office.effective.features.workspace.repository.WorkspaceRepository
import java.time.Instant
import java.util.*

/**
 * Converts between Google Calendar [Event] and [BookingDTO] objects.
 */
class GoogleCalendarConverter(
    private val calendarIdsRepository: CalendarIdsRepository,
    private val userRepository: UserRepository,
    private val workspaceConverter: WorkspaceFacadeConverter,
    private val userConverter: UserDTOModelConverter,
    private val bookingConverter: BookingFacadeConverter,
    private val verifier: UuidValidator,
    private val workspaceRepository: WorkspaceRepository
) {

    private val defaultAccount: String =
        config.propertyOrNull("auth.app.defaultAppEmail")?.getString() ?: throw Exception(
            "Config file does not contain default gmail value"
        )

    /**
     * Converts [Event] to [BookingDTO]
     *
     * Creates placeholders if workspace, owner or participant doesn't exist in database
     *
     * @param event [Event] to be converted
     * @return The resulting [BookingDTO] object
     * @author Danil Kiselev, Max Mishenko, Daniil Zavyalov
     */
    fun toBookingDTO(event: Event): BookingDTO {
        val organizer: String = event.organizer?.email ?: ""
        val email = if (organizer != defaultAccount) organizer else event.description.substringBefore(" ")
        val recurrence = event.recurrence?.toString()?.getRecurrence()?.toDto()
        return BookingDTO(
            owner = getUser(email),
            participants = event.attendees?.filter { !(it?.resource ?: true) }?.map { getUser(it.email) } ?: listOf(),
            workspace = getWorkspace(
                event.attendees?.firstOrNull { it?.resource ?: false }?.email
                    ?: "c_1882249i0l5ieh0cih42dli6fodbi@resource.calendar.google.com" //TODO: Think about a different behavior in case of null
            ),
            id = event.id ?: null,
            beginBooking = event.start?.dateTime?.value ?: 0,//TODO FIX date placeholder
            endBooking = event.end?.dateTime?.value ?: ((event.start?.dateTime?.value ?: 0) + 86400000),
            recurrence = recurrence
        )
    }

    /**
     * Converts [Event] to [Booking]
     *
     * Creates placeholders if workspace or owner doesn't exist in database
     *
     * @param event [Event] to be converted
     * @return The resulting [Booking] object
     * @throws Exception if it fails to get user id from description or workspace id from summary of Google Calendar event
     * @author Danil Kiselev, Max Mishenko, Daniil Zavyalov
     */
    fun toWorkspaceBooking(event: Event): Booking {
        val userId: UUID = try {
            UUID.fromString(event.description.substringBefore(" "))
        } catch (e: Exception) {
            throw Exception("Can't get user UUID from Google Calendar event description. Reason: ${e.printStackTrace()}")
        }
        val workspaceID: UUID = try {
            UUID.fromString(event.summary.substringBefore(" "))
        } catch (e: Exception) {
            throw Exception("Can't get user UUID from Google Calendar event summary. Reason: ${e.printStackTrace()}")
        }
        val recurrence = event.recurrence?.toString()?.getRecurrence()
        return Booking(
            owner = userRepository.findById(userId) ?: UserModel(
                id = null,
                fullName = "Nonexistent user",
                tag = null,
                active = false,
                role = null,
                avatarURL = null,
                integrations = emptySet(),
                email = ""
            ),
            participants = emptyList(),
            workspace = workspaceRepository.findById(workspaceID)
                ?: Workspace(null, "Nonexistent workspace", "placeholder", listOf(), null),
            id = event.id ?: null,
            beginBooking = Instant.ofEpochMilli(event.start?.dateTime?.value ?: 0),//TODO FIX date placeholder
            endBooking = Instant.ofEpochMilli(event.end?.dateTime?.value ?: ((event.start?.dateTime?.value ?: 0) + 86400000)),
            recurrence = recurrence?.toDto()?.let {
                RecurrenceConverter.dtoToModel(it)
            } //TODO: add converter Recurrence to RecurrenceModel, or unite Recurrence and RecurrenceModel to a single class
        )
    }

    /**
     * Find [WorkspaceDTO] by workspace calendar id
     *
     * @param calendarId Google id of calendar of workspace
     * @return [WorkspaceDTO]
     * @author Danil Kiselev, Max Mishenko
     */
    private fun getWorkspace(calendarId: String): WorkspaceDTO {
        val workspaceModel: Workspace = calendarIdsRepository.findWorkspaceById(calendarId) //may return placeholder
        return workspaceConverter.modelToDto(workspaceModel)
    }

    /**
     * Find [UserDTO] by email. May return placeholder if user with the given email doesn't exist in database
     *
     * @param email
     * @return [UserDTO] with data from database or [UserDTO] placeholder with the given [email]
     * @author Danil Kiselev, Max Mishenko, Daniil Zavyalov
     */
    private fun getUser(email: String): UserDTO {
        val userModel: UserModel = userRepository.findByEmail(email)
            ?: UserModel(
                id = null,
                fullName = "Unregistered user",
                tag = null,
                active = false,
                role = null,
                avatarURL = null,
                integrations = emptySet(),
                email = email
            )
        return userConverter.modelToDTO(userModel)
    }

    /**
     * Converts [BookingDTO] to [Event]. [Event.description] is used to indicate the booking author,
     * because [Event.organizer] is [defaultAccount] of application
     *
     * @param dto [BookingDTO] to be converted
     * @return The resulting [Event] object
     * @author Danil Kiselev, Max Mishenko
     */
    fun toGoogleEvent(dto: BookingDTO): Event {
        return Event().apply {
            summary = "${dto.owner.fullName}: create from office application"
            description =
                "${dto.owner.email} - почта организатора"//"${dto.owner.integrations?.first { it.name == "email" }} - почта организатора"
            organizer = dto.owner.toGoogleOrganizer()
            attendees = dto.participants.map { it.toAttendee() } + dto.owner.toAttendee()
                .apply { organizer = true } + dto.workspace.toAttendee()
            if (dto.recurrence != null) recurrence = listOf(dto.recurrence.toRecurrence().rule())
            start = dto.beginBooking.toGoogleDateTime()
            end = dto.endBooking.toGoogleDateTime()
        }
    }

    /**
     * Converts workspace [BookingDTO] to [Event]. [Event.description] is used to indicate the booking author,
     * because [Event.organizer] is [defaultAccount] of application.
     * [Event.summary] is used to indicate the booking workspace.
     *
     * @param dto [BookingDTO] to be converted
     * @return The resulting [Event] object
     * @author Daniil Zavyalov
     */
    fun toGoogleWorkspaceEvent(dto: BookingDTO): Event {
        return Event().apply {
            summary = "${dto.workspace.id} - workspace id"
            description =
                "${dto.owner.id} - organizer id"
            if (dto.recurrence != null) recurrence = listOf(dto.recurrence.toRecurrence().rule())
            start = dto.beginBooking.toGoogleDateTime()
            end = dto.endBooking.toGoogleDateTime()
        }
    }

    /**
     * Converts workspace [Booking] to [Event]. [Event.description] is used to indicate the booking author,
     * because [Event.organizer] is [defaultAccount] of application.
     * [Event.summary] is used to indicate the booking workspace.
     *
     * @param model [BookingDTO] to be converted
     * @return The resulting [Event] object
     * @author Daniil Zavyalov
     */
    fun toGoogleWorkspaceEvent(model: Booking): Event {
        return toGoogleWorkspaceEvent(bookingConverter.modelToDto(model))
    }

    /**
     * Converts meeting room [Booking] to [Event]. [Event.description] is used to indicate the booking author,
     * because [Event.organizer] is [defaultAccount] of application
     *
     * @param model [BookingDTO] to be converted
     * @return The resulting [Event] object
     * @author Danil Kiselev
     */
    fun toGoogleEvent(model: Booking): Event {
        return toGoogleEvent(bookingConverter.modelToDto(model))
    }

    /**
     * Converts [Event] to [Booking]
     *
     * Uses [toBookingDTO]
     *
     * TODO: Event should be created without converting Booking to BookingDTO
     *
     * @param event [Event] to be converted
     * @return The resulting [BookingDTO] object
     * @author Danil Kiselev, Max Mishenko
     */
    fun toBookingModel(event: Event): Booking {
        return bookingConverter.dtoToModel(toBookingDTO(event));
    }

    /**
     * Converts milliseconds to [EventDateTime]
     *
     * @return [EventDateTime]
     * @author Danil Kiselev, Max Mishenko
     */
    private fun Long.toGoogleDateTime(): EventDateTime {
        return EventDateTime().also {
            it.dateTime = DateTime(this)
            it.timeZone = TimeZone.getDefault().id
        }
    }

    /**
     * Converts [UserDTO] of participant to [EventAttendee]
     *
     * @return [EventAttendee]
     * @author Danil Kiselev, Max Mishenko
     */
    private fun UserDTO.toAttendee(): EventAttendee {
        return EventAttendee().also {
            it.email =
                this.email// this.integrations?.first { it.name == "email" }?.value //TODO надо допилить получение почты
        }
    }

    /**
     * Converts [UserDTO] of owner to [Organizer]
     *
     * @return [Organizer]
     * @author Danil Kiselev, Max Mishenko
     */
    private fun UserDTO.toGoogleOrganizer(): Event.Organizer? {
        return Organizer().also {
            it.email =
                this.email//this.integrations?.first { integ -> integ.name == "email" }?.value //TODO надо допилить получение почты
        }
    }

    /**
     * Converts [WorkspaceDTO]to [EventAttendee] (Rooms are also considered participants)
     *
     * @return [EventAttendee]
     * @author Danil Kiselev, Max Mishenko
     */
    private fun WorkspaceDTO.toAttendee(): EventAttendee {
        return EventAttendee().also {
            it.email = getCalendarIdById(id) //TODO надо допилить получение комнаты
            it.resource = true
        }
    }

    /**
     * Finds workspace calendar id by workspace id
     *
     * @param id workspace id. Should be valid UUID
     * @return calendar id by workspace id in database
     * @author Danil Kiselev, Max Mishenko
     */
    private fun getCalendarIdById(id: String): String {
        return calendarIdsRepository.findByWorkspace(verifier.uuidFromString(id))
    }
}