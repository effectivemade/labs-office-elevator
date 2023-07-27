package band.effective.office.elevator.domain.usecase

import band.effective.office.elevator.domain.ProfileRepository
import band.effective.office.elevator.domain.models.User

class UpdateUserUseCase (private val profileRepository: ProfileRepository) {
    suspend fun execute(user: User) {
        return profileRepository.updateUser(user)
    }
}