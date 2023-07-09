package band.effective.office.tablet.network.api

import band.effective.office.tablet.domain.model.RoomInfo
import band.effective.office.tablet.network.model.WebServerEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class WorkApi : Api {
    val mutableRoomInfo = MutableStateFlow(RoomInfo.defaultValue)
    val mutableOrgList = MutableStateFlow(listOf("a", "b", "c"))
    override suspend fun getRoomInfo(): RoomInfo {
        delay(1000L)
        return mutableRoomInfo.value
    }

    override suspend fun getOrganizers(): List<String> {
        delay(1000L)
        return mutableOrgList.value
    }

    override suspend fun cancelEvent(): Boolean {
        delay(1000L)
        return false
    }

    override suspend fun bookingRoom(): Boolean {
        delay(1000L)
        return false
    }

    override fun subscribeOnWebHock(
        scope: CoroutineScope,
        handler: (event: WebServerEvent) -> Unit
    ) {
        scope.launch { mutableRoomInfo.collect { handler(WebServerEvent.RoomInfoUpdate) } }
        scope.launch { mutableOrgList.collect { handler(WebServerEvent.OrganizerInfoUpdate) } }
    }
}