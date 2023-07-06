package tablet.network

import tablet.domain.model.Booking
import tablet.domain.model.EventInfo

interface ISelectRoomRepository {
    suspend fun bookRoom(booking: Booking)
}