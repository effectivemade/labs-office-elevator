package band.effective.office.tablet.network.repository

import band.effective.office.network.model.Either
import band.effective.office.network.model.ErrorResponse
import band.effective.office.tablet.domain.model.EventInfo

interface BookingRepository {
    suspend fun bookingRoom(eventInfo: EventInfo, roomId: String): Either<ErrorResponse, String>
    suspend fun updateBooking(eventInfo: EventInfo, roomId: String): Either<ErrorResponse, String>
}