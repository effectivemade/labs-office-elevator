package band.effective.office.tablet.domain

import band.effective.office.tablet.domain.model.EventInfo
import band.effective.office.tablet.domain.model.RoomInfo
import java.util.Calendar

/**Interface for get information about room and send booking request*/
interface RoomInteractor {
    /**Get room information by room's name*/
    fun getRoomInfo(name: String): RoomInfo

    /**Get list peoples have rights for booking room*/
    fun getOrganizers(): List<String>

    /**Check opportunity book room
     * @return Return null if room is free, if room busy then return interfering event*/
    fun checkRoom(name: String, dateTime: Calendar): EventInfo?
}