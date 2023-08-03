package band.effective.office.tablet.domain

import band.effective.office.tablet.domain.useCase.RoomInfoUseCase
import band.effective.office.tablet.network.repository.CancelRepository
import kotlinx.coroutines.launch

/**Controller implementation NOT following for current event*/
class CurrentEventControllerServerImpl(
    roomUseCase: RoomInfoUseCase,
    cancelRepository: CancelRepository
) : CurrentEventController(roomUseCase, cancelRepository) {
    override fun update() = scope.launch {}
    override fun stopUpdate() {}
}