package band.effective.office.elevator.domain.repository

import band.effective.office.elevator.domain.models.User.UserData

interface UserProfileRepository {
    suspend fun getUser(idToken: String) : UserData
}