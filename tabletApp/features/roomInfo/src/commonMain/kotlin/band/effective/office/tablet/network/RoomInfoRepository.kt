package band.effective.office.tablet.network

import band.effective.office.tablet.domain.model.RoomInfo

interface RoomInfoRepository {
    fun getRoomInfo(name: String): RoomInfo
}