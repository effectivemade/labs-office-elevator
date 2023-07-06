package tablet.domain

import tablet.domain.model.Booking
import tablet.domain.model.EventInfo
import tablet.network.ISelectRoomRepository

class SelectRoomInteractorImpl(private val repository: ISelectRoomRepository): ISelectRoomInteractor {
    override suspend fun bookRoom(booking: Booking)  = repository.bookRoom(booking)
}