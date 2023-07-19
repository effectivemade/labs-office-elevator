package band.effective.office.tablet.network.api

import band.effective.office.tablet.domain.model.RoomInfo
import band.effective.office.tablet.network.model.WebServerEvent
import kotlinx.coroutines.CoroutineScope
import java.util.Calendar

interface Api {
    //TODO(Maksim Mishenko): change return value
    suspend fun getRoomInfo(): RoomInfo
    suspend fun getOrganizers(): List<String>
    suspend fun cancelEvent(): Boolean
    fun subscribeOnWebHock(scope: CoroutineScope,handler: (event: WebServerEvent) -> Unit)
    suspend fun bookingRoom(begin: Calendar, end: Calendar, owner: String): Boolean
}