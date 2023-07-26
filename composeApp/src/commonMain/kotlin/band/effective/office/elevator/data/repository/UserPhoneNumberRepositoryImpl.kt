package band.effective.office.elevator.data.repository

import band.effective.office.elevator.domain.models.UserPhoneNumber
import band.effective.office.elevator.domain.repository.UserPhoneNumberRepository

class UserPhoneNumberRepositoryImpl : UserPhoneNumberRepository {
    override suspend fun callUserPhoneNumber(idToken: String): UserPhoneNumber {
        TODO("Not yet implemented")
    }

    override suspend fun saveUserPhoneNumberInDB(): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun getUserPhoneFromDB(): UserPhoneNumber {
        TODO("Not yet implemented")
    }
}