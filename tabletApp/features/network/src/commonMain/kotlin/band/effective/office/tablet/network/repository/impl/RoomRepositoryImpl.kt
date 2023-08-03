package band.effective.office.tablet.network.repository.impl

import band.effective.office.tablet.domain.model.Either
import band.effective.office.tablet.domain.model.ErrorWithData
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

class RoomRepositoryImpl(private val api: Api) : RoomRepository {
    private val roomInfo: MutableStateFlow<Either<ErrorWithData<RoomInfo>, RoomInfo>?> =
        MutableStateFlow(null)

    private suspend fun loadRoomInfo(room: String): Either<ErrorWithData<RoomInfo>, RoomInfo> {
        val response = api.getRoomInfo(room)
        val save = roomInfo.value
        when (response) {
            is Either.Error -> roomInfo.update {
                Either.Error(
                    ErrorWithData(
                        error = response.error,
                        saveData = when (save) {
                            is Either.Error -> save.error.saveData
                            is Either.Success -> save.data
                            null -> null
                        }
                    )
                )
            }

            is Either.Success -> roomInfo.update { response }
        }
        return roomInfo.value!!
    }

    override suspend fun getRoomInfo(room: String): Either<ErrorWithData<RoomInfo>, RoomInfo> =
        if (roomInfo.value == null)
            loadRoomInfo(room)
        else
            roomInfo.value!!

    override fun subscribeOnUpdates(
        scope: CoroutineScope,
        room: String,
        handler: (Either<ErrorWithData<RoomInfo>, RoomInfo>) -> Unit
    ): Job =
        scope.launch(Dispatchers.IO) {
            api.subscribeOnWebHock(this) {
                if (it is WebServerEvent.RoomInfoUpdate)
                    launch(Dispatchers.IO) { roomInfo.update { loadRoomInfo(room) } }
            }
            roomInfo.collect { if (it != null) handler(it) }
        }
}