package band.effective.office.elevator.domain.usecase

import band.effective.office.elevator.domain.ProfileRepository
import band.effective.office.elevator.domain.models.User

class GetUserByIdUseCase(private val profileRepository: ProfileRepository) {
    suspend fun execute(id:String): User {
        return profileRepository.getUser(id)
    }
}