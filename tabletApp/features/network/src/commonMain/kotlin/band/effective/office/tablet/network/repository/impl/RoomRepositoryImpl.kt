package band.effective.office.tablet.network.repository.impl

import band.effective.office.tablet.domain.model.Either
import band.effective.office.tablet.domain.model.RoomInfo
import band.effective.office.tablet.network.api.Api
import band.effective.office.tablet.network.model.WebServerEvent
import band.effective.office.tablet.network.repository.RoomRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import network.model.ErrorResponse

class RoomRepositoryImpl(private val api: Api) : RoomRepository {
    private val roomInfo: MutableStateFlow<Either<ErrorResponse, RoomInfo>?> =
        MutableStateFlow(null)

    private suspend fun loadRoomInfo() = api.getRoomInfo()
    override suspend fun getRoomInfo(): Either<ErrorResponse, RoomInfo> {
        if (roomInfo.value == null) roomInfo.update { loadRoomInfo() }
        return roomInfo.value!!
    }

    override fun subscribeOnUpdates(
        scope: CoroutineScope,
        handler: (Either<ErrorResponse, RoomInfo>) -> Unit
    ): Job =
        scope.launch(Dispatchers.IO) {
            //roomInfo.update { loadRoomInfo() }
            api.subscribeOnWebHock(this) {
                if (it is WebServerEvent.RoomInfoUpdate)
                    launch(Dispatchers.IO) { roomInfo.update { loadRoomInfo() } }
            }
            roomInfo.collect { if (it != null) handler(it) }
        }
}