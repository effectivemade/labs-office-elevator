package band.effective.office.tablet.domain

import band.effective.office.tablet.domain.model.Booking


interface ISelectRoomInteractor {
    suspend fun bookRoom(booking: Booking)
}