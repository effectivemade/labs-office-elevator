package band.effective.office.elevator.domain.useCase

import band.effective.office.elevator.domain.repository.ProfileRepository

class GetUserUseCase(private val userProfileRepository: ProfileRepository) {
    suspend fun execute(idToken: String)  = userProfileRepository.getUser()
}