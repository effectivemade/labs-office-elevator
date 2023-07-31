package band.effective.office.elevator.domain.usecase

import band.effective.office.elevator.domain.ProfileRepository
import band.effective.office.elevator.domain.models.User

class GetUserByIdUseCase(private val profileRepository: ProfileRepository) {
    suspend fun execute(id:String) : User {
        return profileRepository.getUser(id)
    }

    suspend fun executeInFormat(id: String):User{
        val user = profileRepository.getUser(id)
        return User(id = user.id, imageUrl = user.imageUrl, userName = user.userName,
            post = user.post, phoneNumber = user.phoneNumber.substring(3), email = user.email,
            telegram = user.telegram.substring(1))
    }
}