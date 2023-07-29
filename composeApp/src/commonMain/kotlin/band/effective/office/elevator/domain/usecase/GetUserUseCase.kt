package band.effective.office.elevator.domain.usecase

import band.effective.office.elevator.domain.models.UserData
import band.effective.office.elevator.domain.repository.UserProfileRepository

class GetUserUseCase(private val userProfileRepository: UserProfileRepository) {
    suspend fun execute(idToken: String): UserData = userProfileRepository.getUser(idToken)
}