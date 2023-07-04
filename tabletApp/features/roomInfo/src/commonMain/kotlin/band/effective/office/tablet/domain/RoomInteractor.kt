package band.effective.office.tablet.domain

import band.effective.office.tablet.domain.model.RoomInfo

/**Interface for get information about room and send booking request*/
interface RoomInteractor {
    /**Get room information by room's name*/
    fun getRoomInfo(name: String): RoomInfo
}