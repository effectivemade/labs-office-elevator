package band.effective.office.tablet.domain

import band.effective.office.tablet.domain.model.Booking

interface SelectRoomInteractor {
    suspend fun bookRoom(booking: Booking)
}