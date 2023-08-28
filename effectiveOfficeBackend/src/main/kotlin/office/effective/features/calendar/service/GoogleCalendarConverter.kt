package office.effective.features.calendar.service

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
import office.effective.features.booking.converters.BookingFacadeConverter
import office.effective.features.calendar.repository.CalendarRepository
import office.effective.features.user.converters.UserDTOModelConverter
import office.effective.features.user.repository.UserRepository
import office.effective.features.workspace.converters.WorkspaceFacadeConverter
import office.effective.model.Booking
import office.effective.model.UserModel
import office.effective.model.Workspace
import utils.RecurrenceRuleFactory.getRecurrence
import utils.RecurrenceRuleFactory.rule

class GoogleCalendarConverter(
    private val calendarRepository: CalendarRepository,
    private val userRepository: UserRepository,
    private val workspaceConverter: WorkspaceFacadeConverter,
    private val userConverter: UserDTOModelConverter,
    private val bookingConverter: BookingFacadeConverter,
    private val verifier: UuidValidator
) {

    private val defaultAccount: String =
        config.propertyOrNull("auth.app.defaultAppEmail")?.getString() ?: throw Exception(
            "Config file does not contain default gmail value"
        )

    fun toBookingDTO(event: Event): BookingDTO {
        val a: String = event.organizer?.email ?: ""
        val email = if (a != defaultAccount) a else event.description.substringBefore(" ")
        val recurrence = event.recurrence?.toString()?.getRecurrence()?.toDto()
        return BookingDTO(
            owner = getUser(email),
            participants = event.attendees?.filter { !(it?.resource ?: true) }?.map { getUser(it.email) } ?: listOf(),
            workspace = getWorkspace(
                event.attendees?.firstOrNull { it?.resource ?: false }?.email
                    ?: "c_1882249i0l5ieh0cih42dli6fodbi@resource.calendar.google.com"
            ),
            id = event.id ?: null,
            beginBooking = event.start?.dateTime?.value ?: 0,//TODO FIX date placeholder
            endBooking = event.end?.dateTime?.value ?: ((event.start?.dateTime?.value ?: 0) + 86400000),
            recurrence = recurrence
        )
    }

    private fun getWorkspace(calendarId: String): WorkspaceDTO {
        // достаём воркспейс по гугловкому id
        val workspaceModel: Workspace = calendarRepository.findWorkspaceById(calendarId)
        return workspaceConverter.modelToDto(workspaceModel)
    }

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

    fun toGoogleEvent(booking: Booking): Event {
        return toGoogleEvent(bookingConverter.modelToDto(booking))
    }

    fun toBookingModel(event: Event): Booking {
        return bookingConverter.dtoToModel(toBookingDTO(event));
    }

    private fun Long.toGoogleDateTime(): EventDateTime {
        return EventDateTime().also {
            it.dateTime = DateTime(this)
            it.timeZone = "Asia/Omsk"
        }
    }

    private fun UserDTO.toAttendee(): EventAttendee {
        return EventAttendee().also {
            it.email =
                this.email// this.integrations?.first { it.name == "email" }?.value //TODO надо допилить получение почты
        }
    }

    private fun UserDTO.toGoogleOrganizer(): Event.Organizer? {
        return Organizer().also {
            it.email =
                this.email//this.integrations?.first { integ -> integ.name == "email" }?.value //TODO надо допилить получение почты
        }
    }

    private fun WorkspaceDTO.toAttendee(): EventAttendee {
        return EventAttendee().also {
            it.email = getCalendarIdById(id) //TODO надо допилить получение комнаты
            it.resource = true
        }
    }

    private fun getCalendarIdById(id: String): String {
        return calendarRepository.findByWorkspace(verifier.uuidFromString(id))
    }
}