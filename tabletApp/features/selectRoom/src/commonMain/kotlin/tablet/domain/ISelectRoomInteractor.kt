package tablet.domain

import tablet.domain.model.Booking
import tablet.domain.model.EventInfo

interface ISelectRoomInteractor {
    suspend fun bookRoom(booking: Booking)
}