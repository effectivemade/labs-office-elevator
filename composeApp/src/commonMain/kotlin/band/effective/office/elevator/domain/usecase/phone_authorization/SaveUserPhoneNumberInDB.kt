package band.effective.office.elevator.domain.usecase.phone_authorization

import band.effective.office.elevator.domain.repository.UserPhoneNumberRepository

class SaveUserPhoneNumberInDB(private val userPhoneNumberRepository: UserPhoneNumberRepository) {
    fun execute(): Boolean {
        TODO()
    }
}