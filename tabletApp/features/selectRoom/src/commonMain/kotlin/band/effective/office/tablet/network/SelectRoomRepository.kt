package band.effective.office.tablet.network

import band.effective.office.tablet.domain.model.Booking

interface SelectRoomRepository {
    suspend fun bookRoom(booking: Booking)
}