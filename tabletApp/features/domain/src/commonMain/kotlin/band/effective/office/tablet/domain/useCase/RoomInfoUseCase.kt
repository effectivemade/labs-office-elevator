package band.effective.office.tablet.domain.useCase

import band.effective.office.tablet.domain.model.RoomInfo
import band.effective.office.tablet.network.repository.RoomRepository
import kotlinx.coroutines.CoroutineScope

class RoomInfoUseCase(private val repository: RoomRepository) {
    suspend operator fun invoke() = repository.getRoomInfo()
    fun subscribe(scope: CoroutineScope, handler: (RoomInfo) -> Unit) {
        repository.init(scope)
        repository.subscribeOnUpdates { handler(it) }
    }
}