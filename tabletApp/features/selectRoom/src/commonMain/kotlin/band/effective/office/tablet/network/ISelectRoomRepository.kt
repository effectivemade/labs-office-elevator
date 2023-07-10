package band.effective.office.tablet.network

import band.effective.office.tablet.domain.model.Booking
import band.effective.office.tablet.domain.model.EventInfo

interface ISelectRoomRepository {
    suspend fun bookRoom(booking: Booking)
}