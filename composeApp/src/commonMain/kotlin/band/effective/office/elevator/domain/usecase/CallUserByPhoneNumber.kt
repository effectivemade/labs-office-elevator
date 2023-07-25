package band.effective.office.elevator.domain.usecase

import band.effective.office.elevator.domain.models.UserPhoneNumber
import band.effective.office.elevator.domain.repository.UserPhoneNumberRepository

class CallUserByPhoneNumber(private val userPhoneNumberRepository: UserPhoneNumberRepository) {
    fun execute(idToken: String): UserPhoneNumber {
        TODO()
    }
}