package band.effective.office.tablet.network.repository.impl

import band.effective.office.tablet.domain.model.RoomInfo
import band.effective.office.tablet.network.api.Api
import band.effective.office.tablet.network.model.WebServerEvent
import band.effective.office.tablet.network.repository.RoomRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RoomRepositoryImpl(private val api: Api) : RoomRepository {
    private val roomInfo: MutableStateFlow<RoomInfo> = MutableStateFlow(RoomInfo.defaultValue)
    private var scope: CoroutineScope? = null
    override suspend fun getRoomInfo() = api.getRoomInfo()
    override fun subscribeOnUpdates(handler: (RoomInfo) -> Unit) =
        scope?.launch(Dispatchers.IO) {
            roomInfo.collect { handler(it) }
        }

    override fun init(scope: CoroutineScope) {
        this.scope = scope
        scope.launch(Dispatchers.IO) { roomInfo.update { getRoomInfo() } }
        api.subscribeOnWebHock(scope) {
            if (it is WebServerEvent.RoomInfoUpdate)
                scope.launch(Dispatchers.IO) { roomInfo.update { getRoomInfo() } }
        }

    }
}