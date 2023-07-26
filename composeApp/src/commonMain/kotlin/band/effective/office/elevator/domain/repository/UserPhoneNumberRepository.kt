package band.effective.office.elevator.domain.repository

import band.effective.office.elevator.domain.models.UserPhoneNumber

interface UserPhoneNumberRepository {
    suspend fun callUserPhoneNumber(idToken: String) : UserPhoneNumber

    suspend fun saveUserPhoneNumberInDB() : Boolean

    suspend fun getUserPhoneFromDB() : UserPhoneNumber
}