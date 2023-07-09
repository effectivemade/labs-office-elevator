package band.effective.office.tablet.domain.useCase

import band.effective.office.tablet.domain.CurrentEventController
import band.effective.office.tablet.domain.model.RoomInfo
import band.effective.office.tablet.network.repository.ServerUpdateRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class UpdateUseCase(
    private val roomInfoUseCase: RoomInfoUseCase,
    private val organizersInfoUseCase: OrganizersInfoUseCase,
    private val currentEventController: CurrentEventController,
    private val serverUpdateRepository: ServerUpdateRepository
) {
    suspend fun invoke(
        scope: CoroutineScope,
        roomUpdateHandler: (RoomInfo) -> Unit,
        organizerUpdateHandler: (List<String>) -> Unit
    ) {
        serverUpdateRepository.subscribeOnUpdates(scope, {
            roomUpdateHandler(roomUpdate(scope))
        }, {
            organizerUpdateHandler(organizerUpdate(scope))
        })
        currentEventController.subscribe {
            roomUpdateHandler(roomUpdate(scope))
        }
    }

    private fun roomUpdate(scope: CoroutineScope): RoomInfo {
        var newRoomInfo: RoomInfo? = null
        scope.launch {
            newRoomInfo = roomInfoUseCase()
        }
        while (newRoomInfo == null) {
        }
        return newRoomInfo!!
    }

    private fun organizerUpdate(
        scope: CoroutineScope
    ): List<String> {
        var newOrgListInfo: List<String>? = null
        scope.launch {
            newOrgListInfo = organizersInfoUseCase()
        }
        while (newOrgListInfo == null) {
        }
        return newOrgListInfo!!
    }
}