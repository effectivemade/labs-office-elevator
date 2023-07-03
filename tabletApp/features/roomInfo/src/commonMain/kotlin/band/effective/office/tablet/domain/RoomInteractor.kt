package band.effective.office.tablet.domain

import band.effective.office.tablet.domain.model.RoomInfo

interface RoomInteractor {
    fun getRoomInfo(name: String): RoomInfo
}