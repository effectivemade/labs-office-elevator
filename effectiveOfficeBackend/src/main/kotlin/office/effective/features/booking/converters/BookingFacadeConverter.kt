package office.effective.features.booking.converters

import office.effective.features.booking.dto.BookingDTO
import office.effective.features.user.converters.UserDTOModelConverter
import office.effective.features.workspace.converters.WorkspaceFacadeConverter
import office.effective.model.Booking

class BookingFacadeConverter(private val userConverter: UserDTOModelConverter,
                             private val workspaceConverter: WorkspaceFacadeConverter) {
    fun modelToDto(booking: Booking): BookingDTO {
        return BookingDTO(
            owner = userConverter.modelToDTO(booking.owner),
            participants = booking.participants.map { userConverter.modelToDTO(it) },
            workspace = workspaceConverter.workspaceModelToDto(booking.workspace),
            id = booking.id.toString(),
            beginBooking = booking.beginBooking.epochSecond,
            endBooking = booking.endBooking.epochSecond
        )
    }
}