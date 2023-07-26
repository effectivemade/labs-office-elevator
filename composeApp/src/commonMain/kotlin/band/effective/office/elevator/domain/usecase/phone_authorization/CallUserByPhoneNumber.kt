package band.effective.office.elevator.domain.usecase.phone_authorization

import band.effective.office.elevator.domain.models.UserPhoneNumber
import band.effective.office.elevator.domain.repository.UserPhoneNumberRepository

class CallUserByPhoneNumber(private val userPhoneNumberRepository: UserPhoneNumberRepository) {
    suspend fun execute(idToken: String): UserPhoneNumber {
        return userPhoneNumberRepository.callUserPhoneNumber(idToken)
    }
}