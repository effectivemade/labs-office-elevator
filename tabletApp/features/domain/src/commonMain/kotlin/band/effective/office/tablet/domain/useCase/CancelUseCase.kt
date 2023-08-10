package band.effective.office.tablet.domain.useCase

import band.effective.office.tablet.domain.model.EventInfo
import band.effective.office.tablet.network.repository.CancelRepository

class CancelUseCase(private val cancelRepository: CancelRepository) {
    suspend operator fun invoke(eventInfo: EventInfo) = cancelRepository.cancelEvent(eventInfo)
}