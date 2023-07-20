package band.effective.office.tablet.domain

import band.effective.office.tablet.domain.model.Booking
import band.effective.office.tablet.network.SelectRoomRepository

class SelectRoomInteractorImpl(private val repository: SelectRoomRepository):
    SelectRoomInteractor {
    override suspend fun bookRoom(booking: Booking)  = repository.bookRoom(booking)
}