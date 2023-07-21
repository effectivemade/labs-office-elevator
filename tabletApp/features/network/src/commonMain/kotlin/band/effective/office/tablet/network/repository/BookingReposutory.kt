package band.effective.office.tablet.network.repository

import band.effective.office.tablet.domain.model.EventInfo

interface BookingRepository {
    suspend fun bookingRoom(eventInfo: EventInfo): Boolean
}