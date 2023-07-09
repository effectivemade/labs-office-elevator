package band.effective.office.tablet.network.repository

import band.effective.office.tablet.domain.model.RoomInfo

interface RoomRepository {
    suspend fun getRoomInfo(): RoomInfo
}