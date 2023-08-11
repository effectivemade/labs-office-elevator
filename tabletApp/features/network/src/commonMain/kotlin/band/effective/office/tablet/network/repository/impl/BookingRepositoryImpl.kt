package band.effective.office.tablet.network.repository.impl

import band.effective.office.network.api.Api
import band.effective.office.network.dto.BookingInfo
import band.effective.office.network.model.Either
import band.effective.office.network.model.ErrorResponse
import band.effective.office.tablet.domain.model.EventInfo
import band.effective.office.tablet.network.repository.BookingRepository
import band.effective.office.tablet.utils.map

class BookingRepositoryImpl(private val api: Api) :
    BookingRepository {
    override suspend fun bookingRoom(
        eventInfo: EventInfo,
        roomId: String
    ): Either<ErrorResponse, String> = api.createBooking(eventInfo.toBookingInfo(roomId))
        .map(errorMapper = { it }, successMapper = { it.status })

    override suspend fun updateBooking(
        eventInfo: EventInfo,
        roomId: String
    ): Either<ErrorResponse, String> =
        api.updateBooking(eventInfo.toBookingInfo(roomId))
            .map(errorMapper = { it }, successMapper = { it.status })


    private fun EventInfo.toBookingInfo(roomId: String): BookingInfo = BookingInfo(
        id = id,
        begin = this.startTime.timeInMillis,
        end = this.finishTime.timeInMillis,
        ownerId = this.organizer.id,
        participants = listOf(),
        workspaceId = roomId
    )
}
