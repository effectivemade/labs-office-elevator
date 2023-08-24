package band.effective.office.tablet.domain.useCase

import band.effective.office.network.model.Either
import band.effective.office.tablet.domain.CurrentEventController
import band.effective.office.tablet.domain.model.ErrorWithData
import band.effective.office.tablet.domain.model.Organizer
import band.effective.office.tablet.domain.model.RoomInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**Use case for tracking for updates*/
class UpdateUseCase(
    private val roomInfoUseCase: RoomInfoUseCase,
    private val organizersInfoUseCase: OrganizersInfoUseCase,
    private val checkSettingsUseCase: CheckSettingsUseCase,
    private val currentEventController: CurrentEventController
) {
    /**Get fresh room info*/
    suspend fun getRoomInfo(nameRoom: String) = roomInfoUseCase(nameRoom)

    /**Get fresh org list*/
    suspend fun getOrganizersList() = organizersInfoUseCase()

    /**Subscribe on updates
     * @param scope scope for collect information
     * @param roomUpdateHandler handler for room event update
     * @param organizerUpdateHandler handler for org list update*/
    operator fun invoke(
        scope: CoroutineScope,
        roomUpdateHandler: (Either<ErrorWithData<RoomInfo>, RoomInfo>) -> Unit,
        organizerUpdateHandler: (Either<ErrorWithData<List<Organizer>>, List<Organizer>>) -> Unit
    ) {
        scope.launch { roomInfoUseCase.subscribe(checkSettingsUseCase(),scope).collect { roomUpdateHandler(it) } }
        scope.launch { organizersInfoUseCase.subscribe(scope).collect() { organizerUpdateHandler(it) } }
        currentEventController.start(scope)
        currentEventController.subscribe {
            scope.launch(Dispatchers.IO) {
                roomUpdateHandler(roomInfoUseCase())
            }
        }
    }
}