package band.effective.office.elevator.domain.usecase

import band.effective.office.elevator.domain.UserRepository

class GetLastUserIdUseCase (private val userRepository: UserRepository){
    suspend fun execute():String{
       return userRepository.getLastUserId()
    }
}