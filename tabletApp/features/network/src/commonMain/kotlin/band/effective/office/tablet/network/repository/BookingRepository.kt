package band.effective.office.tablet.network.repository

import band.effective.office.network.model.Either
import band.effective.office.network.model.ErrorResponse
import band.effective.office.tablet.domain.model.EventInfo
import band.effective.office.tablet.domain.model.RoomInfo

interface BookingRepository {
    suspend fun bookingRoom(eventInfo: EventInfo, room: RoomInfo): Either<ErrorResponse, String>
    suspend fun updateBooking(eventInfo: EventInfo, room: RoomInfo): Either<ErrorResponse, String>
}