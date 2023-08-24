package office.effective.features.booking.converters

import model.RecurrenceDTO
import office.effective.common.utils.UuidValidator
import office.effective.dto.BookingDTO
import office.effective.features.calendar.converters.RecurrenceConverter
import office.effective.features.user.converters.UserDTOModelConverter
import office.effective.features.workspace.converters.WorkspaceFacadeConverter
import office.effective.model.Booking
import office.effective.model.RecurrenceModel
import java.time.Instant

class BookingFacadeConverter(private val userConverter: UserDTOModelConverter,
                             private val workspaceConverter: WorkspaceFacadeConverter) {
    /**
     * Converts WorkspaceEntity to BookingDTO
     *
     * @author Daniil Zavyalov
     */
    fun modelToDto(booking: Booking): BookingDTO {
        var recurrenceDTO : RecurrenceDTO? = null
        if(booking.recurrence != null) {
            recurrenceDTO = RecurrenceConverter.modelToDto(booking.recurrence!!)
        }
        return BookingDTO(
            owner = userConverter.modelToDTO(booking.owner),
            participants = booking.participants.map { userConverter.modelToDTO(it) },
            workspace = workspaceConverter.modelToDto(booking.workspace),
            id = booking.id.toString(),
            beginBooking = booking.beginBooking.toEpochMilli(),
            endBooking = booking.endBooking.toEpochMilli(),
            recurrence = recurrenceDTO
        )
    }

    /**
     * Converts BookingDTO to Booking
     *
     * @author Daniil Zavyalov
     */
    fun dtoToModel(bookingDTO: BookingDTO): Booking {
        var recurrenceModel : RecurrenceModel? = null
        if(bookingDTO.recurrence != null) {
            recurrenceModel = RecurrenceConverter.dtoToModel(bookingDTO.recurrence)
        }
        return Booking(
            owner = userConverter.dTOToModel(bookingDTO.owner),
            participants = bookingDTO.participants.map { userConverter.dTOToModel(it) },
            workspace = workspaceConverter.dtoToModel(bookingDTO.workspace),
            id = bookingDTO.id,
            beginBooking = Instant.ofEpochMilli(bookingDTO.beginBooking),
            endBooking = Instant.ofEpochMilli(bookingDTO.endBooking),
            recurrence = recurrenceModel
        )
    }
}