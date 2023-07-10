package band.effective.office.tablet.domain

import band.effective.office.tablet.domain.model.Booking
import band.effective.office.tablet.domain.model.EventInfo

interface ISelectRoomInteractor {
    suspend fun bookRoom(booking: Booking)
}