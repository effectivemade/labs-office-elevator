package band.effective.office.elevator.domain.usecase

import band.effective.office.elevator.domain.models.UserPhoneNumber
import band.effective.office.elevator.domain.repository.UserPhoneNumberRepository

class GetUserPhoneNumber(private val userPhoneNumberRepository: UserPhoneNumberRepository) {
    fun execute(): UserPhoneNumber {
        TODO()
    }
}