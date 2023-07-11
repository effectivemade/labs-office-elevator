package band.effective.office.tablet.network

import band.effective.office.tablet.domain.model.Booking

interface ISelectRoomRepository {
    suspend fun bookRoom(booking: band.effective.office.tablet.domain.model.Booking)
}