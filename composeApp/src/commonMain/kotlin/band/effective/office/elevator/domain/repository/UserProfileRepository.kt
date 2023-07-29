package band.effective.office.elevator.domain.repository

import band.effective.office.elevator.domain.models.UserData

interface UserProfileRepository {
    suspend fun getUser(idToken: String) : UserData

    suspend fun pushUser(userData: UserData) : Boolean
}