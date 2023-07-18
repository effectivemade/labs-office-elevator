package band.effective.office.tablet.domain

import band.effective.office.tablet.domain.model.Booking
import band.effective.office.tablet.domain.model.EventInfo
import band.effective.office.tablet.network.ISelectRoomRepository

class SelectRoomInteractorImpl(private val repository: ISelectRoomRepository):
    ISelectRoomInteractor {
    override suspend fun bookRoom(booking: Booking)  = repository.bookRoom(booking)
}