package band.effective.office.elevator.domain.usecase

import band.effective.office.elevator.domain.ProfileRepository
import band.effective.office.elevator.domain.models.User

class UpdateUserUseCase (private val profileRepository: ProfileRepository) {
    suspend fun execute(user: User){
        profileRepository.updateUser(User(
            id = user.id,
            imageUrl = user.imageUrl,
            userName = user.userName,
            email = user.email,
            phoneNumber = "+7"+user.phoneNumber,
            telegram = "@"+user.telegram,
            post = user.post
        ))
    }
}