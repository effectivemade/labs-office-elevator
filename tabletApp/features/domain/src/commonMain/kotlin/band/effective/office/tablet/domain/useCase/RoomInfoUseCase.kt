package band.effective.office.tablet.domain.useCase

import band.effective.office.tablet.domain.model.RoomInfo
import band.effective.office.tablet.network.repository.RoomRepository
import kotlinx.coroutines.CoroutineScope

/**Use case for get info about room*/
class RoomInfoUseCase(private val repository: RoomRepository) {
    suspend operator fun invoke() = repository.getRoomInfo()
    /**Subscribe on changes information
     * @param scope scope for collect new information
     * @param handler handler for new information*/
    fun subscribe(scope: CoroutineScope, handler: (RoomInfo) -> Unit) {
        repository.subscribeOnUpdates(scope) { handler(it) }
    }
}