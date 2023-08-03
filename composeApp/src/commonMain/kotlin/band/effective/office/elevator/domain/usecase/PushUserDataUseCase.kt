package band.effective.office.elevator.domain.usecase

import band.effective.office.elevator.domain.models.UserData
import band.effective.office.elevator.domain.repository.UserProfileRepository

class PushUserDataUseCase(private val userProfileRepository: UserProfileRepository) {
    suspend fun execute(userData: UserData): Boolean = userProfileRepository.pushUser(userData)
}