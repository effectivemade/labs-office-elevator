package band.effective.office.tablet.network

import band.effective.office.tablet.domain.model.RoomInfo

/**Repository for get information about rooms*/
interface RoomInfoRepository {
    /**Get information about room by room's name*/
    fun getRoomInfo(name: String): RoomInfo

    fun getOrganizers(): List<String>
}