package band.effective.office.tablet.network.repository.impl

import band.effective.office.network.api.Api
import band.effective.office.network.dto.BookingDTO
import band.effective.office.network.model.Either
import band.effective.office.network.model.ErrorResponse
import band.effective.office.tablet.domain.model.EventInfo
import band.effective.office.tablet.domain.model.RoomInfo
import band.effective.office.tablet.network.repository.BookingRepository
import band.effective.office.tablet.utils.Converter.toDto
import band.effective.office.tablet.utils.map

class BookingRepositoryImpl(private val api: Api) :
    BookingRepository {
    override suspend fun bookingRoom(
        eventInfo: EventInfo,
        room: RoomInfo
    ): Either<ErrorResponse, String> = api.createBooking(eventInfo.toBookingInfo(room))
        .map(errorMapper = { it }, successMapper = { it.status })

    override suspend fun updateBooking(
        eventInfo: EventInfo,
        room: RoomInfo
    ): Either<ErrorResponse, String> =
        api.updateBooking(eventInfo.toBookingInfo(room))
            .map(errorMapper = { it }, successMapper = { it.status })


    private fun EventInfo.toBookingInfo(room: RoomInfo): BookingDTO = BookingDTO(
        id = id,
        beginBooking = this.startTime.timeInMillis,
        endBooking = this.finishTime.timeInMillis,
        owner = this.organizer.toDto(),
        participants = listOf(),
        workspace = room.toDto()
    )

}


