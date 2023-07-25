package band.effective.office.elevator.domain.repository

import band.effective.office.elevator.domain.models.UserPhoneNumber

interface UserPhoneNumberRepository {
    fun registerUserByPhoneNumber() : Boolean

    fun saveUserPhoneNumberInDB() : Boolean

    fun getUserPhoneFromDB() : UserPhoneNumber
}