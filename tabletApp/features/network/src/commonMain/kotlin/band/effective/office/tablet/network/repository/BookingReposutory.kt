package band.effective.office.tablet.network.repository

import band.effective.office.tablet.domain.model.Either
import band.effective.office.tablet.domain.model.EventInfo
import network.model.ErrorResponse

interface BookingRepository {
    suspend fun bookingRoom(eventInfo: EventInfo, room: String): Either<ErrorResponse, String>
}