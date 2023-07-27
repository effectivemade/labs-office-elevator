package band.effective.office.elevator.domain.usecase.phone_authorization

import band.effective.office.elevator.domain.models.User.UserData
import band.effective.office.elevator.domain.repository.UserProfileRepository

class GetUserUseCase(private val userProfileRepository: UserProfileRepository) {
    suspend fun execute(idToken: String): UserData = userProfileRepository.getUser(idToken)
}