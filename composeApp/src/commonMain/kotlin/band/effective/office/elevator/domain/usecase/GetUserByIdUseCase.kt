package band.effective.office.elevator.domain.usecase

import band.effective.office.elevator.domain.ProfileRepository
import band.effective.office.elevator.domain.models.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetUserByIdUseCase(private val profileRepository: ProfileRepository) {
    suspend fun execute(id:String) : Flow<User> = profileRepository.getUser(id)
    suspend fun executeInFormat(id: String):Flow<User> = flow{
       var userWithoutFormat = User.defaultUser
           profileRepository.getUser(id).collect{
               user -> userWithoutFormat = user
           }
        emit(User(
            id = userWithoutFormat.id,
            imageUrl = userWithoutFormat.imageUrl,
            userName = userWithoutFormat.userName,
            post = userWithoutFormat.post,
            phoneNumber = userWithoutFormat.phoneNumber.substring(3).replace("-",""),
            telegram = userWithoutFormat.telegram.substring(1),
            email = userWithoutFormat.email
            ))
    }
}