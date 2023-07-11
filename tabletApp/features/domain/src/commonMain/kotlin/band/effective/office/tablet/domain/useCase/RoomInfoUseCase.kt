package band.effective.office.tablet.domain.useCase

import band.effective.office.tablet.network.repository.RoomRepository

class RoomInfoUseCase(private val repository: RoomRepository) {
    suspend operator fun invoke() = repository.getRoomInfo()
}