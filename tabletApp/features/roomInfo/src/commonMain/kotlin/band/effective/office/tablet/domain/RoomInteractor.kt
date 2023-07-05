package band.effective.office.tablet.domain

import band.effective.office.tablet.domain.model.EventInfo
import band.effective.office.tablet.domain.model.RoomInfo
import java.util.Calendar

/**Interface for get information about room and send booking request*/
interface RoomInteractor {
    /**Get room information by room's name*/
    fun getRoomInfo(name: String): RoomInfo

    fun getOrganizers(): List<String>

    fun checkRoom(name: String, dateTime: Calendar): EventInfo?
}