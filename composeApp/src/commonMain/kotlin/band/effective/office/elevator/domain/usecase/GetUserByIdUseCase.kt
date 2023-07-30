package band.effective.office.elevator.domain.usecase

import band.effective.office.elevator.domain.ProfileRepository
import band.effective.office.elevator.domain.models.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetUserByIdUseCase(private val profileRepository: ProfileRepository) {
    suspend fun execute(id:String) : Flow<User> = profileRepository.getUser(id)
}