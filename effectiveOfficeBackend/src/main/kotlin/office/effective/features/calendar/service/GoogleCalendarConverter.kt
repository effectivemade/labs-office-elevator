package utils

import com.google.api.client.util.DateTime
import com.google.api.services.calendar.model.Event
import com.google.api.services.calendar.model.Event.Organizer
import com.google.api.services.calendar.model.EventAttendee
import com.google.api.services.calendar.model.EventDateTime
import model.Recurrence.Companion.toRecurrence
import office.effective.dto.BookingDTO
import office.effective.dto.UserDTO
import office.effective.dto.WorkspaceDTO
import office.effective.features.booking.converters.BookingFacadeConverter
import office.effective.features.calendar.repository.CalendarRepository
import office.effective.features.workspace.converters.WorkspaceFacadeConverter
import office.effective.model.Booking
import org.koin.core.context.GlobalContext
import utils.RecurrenceRuleFactory.getRecurrence
import utils.RecurrenceRuleFactory.rule

object GoogleCalendarConverter {
    fun Event.toBookingDTO(): BookingDTO {
        val email =
            if (organizer.email != "maxim.mishchenko@effective.band") organizer.email else description.substringAfter(" ") // TODO поменть почту
        val recurrence = recurrence?.toString()?.getRecurrence()?.toDto()
        return BookingDTO(
            owner = getUser(email),
            participants = attendees.filter { !it.resource }.map { getUser(it.email) },
            workspace = getWorkspace(attendees.first { it.resource }.email),
            id = null,
            beginBooking = start.dateTime.value,
            endBooking = end.dateTime.value,
            recurrence = recurrence
        )
    }

    private fun getWorkspace(calendarId: String): WorkspaceDTO {
        // достаём воркспейс по гугловкому id
        val calendarRepository : CalendarRepository = GlobalContext.get().get()
        val workspaceModel = calendarRepository.findWorkspaceById(calendarId)
        val workspaceConverter : WorkspaceFacadeConverter = GlobalContext.get().get()
        return workspaceConverter.modelToDto(workspaceModel)
    }

    private fun getUser(email: String): UserDTO {
        TODO("тут достаём пользователя по почте")
    }

    fun BookingDTO.toGoogleEvent(): Event {
        val dto = this
        return Event().apply {
            summary = "${dto.owner.fullName}: create from office application"
            description = "${dto.owner.integrations?.first { it.name == "email" }} - почта организатора"
            organizer = owner.toGoogleOrganizer()
            attendees = participants.map { it.toAttendee() } + owner.toAttendee()
                .apply { organizer = true } + workspace.toAttendee()
            if (dto.recurrence != null) recurrence = listOf(dto.recurrence.toRecurrence().rule())
            start = beginBooking.toGoogleDateTime()
            end = endBooking.toGoogleDateTime()
        }
    }

    fun Booking.toGoogleEvent(): Event {
        val converter: BookingFacadeConverter = GlobalContext.get().get()
        return converter.modelToDto(this).toGoogleEvent()
    }

    private fun Long.toGoogleDateTime(): EventDateTime? {
        return EventDateTime().also {
            it.dateTime = DateTime(this)
            it.timeZone = "Asia/Omsk"
        }
    }

    private fun UserDTO.toAttendee(): EventAttendee {
        return EventAttendee().also {
            it.email = this.integrations?.first { it.name == "email" }?.value //TODO надо допилить получение почты
        }
    }

    private fun UserDTO.toGoogleOrganizer(): Event.Organizer? {
        return Organizer().also {
            it.email =
                this.integrations?.first { integ -> integ.name == "email" }?.value //TODO надо допилить получение почты
        }
    }

    private fun WorkspaceDTO.toAttendee(): EventAttendee {
        return EventAttendee().also {
            it.email = getCalendarIdByName(name) //TODO надо допилить получение комнаты
            it.resource = true
        }
    }

    private fun getCalendarIdByName(name: String) = when (name) {
        "Arrakis" -> "c_18896d17tj6o4j9qio8slq7s32bd4@resource.calendar.google.com"
        "Cassiopeia" -> "c_188402h07rt7ejmujou8j79d484bs@resource.calendar.google.com"
        "Mars" -> "c_188ct184mnbo6ijsg5o7ae8mfvf1u@resource.calendar.google.com"
        "Mercury" -> "c_188444369cdacjcslqgv012ln82tc@resource.calendar.google.com"
        "Moon" -> "c_1882cjltsktv6ivcg1dmbkfu4vgpg@resource.calendar.google.com"
        "Pluto" -> "c_18861ormgerhuhdsg62v1ut3fumnk@resource.calendar.google.com"
        "Sirius" -> "c_1880nb2u1nccqj5ok38jaj4qdmd8e@resource.calendar.google.com"
        "Sun" -> "c_1882249i0l5ieh0cih42dli6fodbi@resource.calendar.google.com"
        "Antares" -> "c_188e47o7itpa4j46nsn6dmedjsd3a@resource.calendar.google.com"
        else -> ""
    }
}