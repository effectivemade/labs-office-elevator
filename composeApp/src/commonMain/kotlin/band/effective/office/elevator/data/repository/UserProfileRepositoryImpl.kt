package band.effective.office.elevator.data.repository

import band.effective.office.elevator.domain.models.UserData
import band.effective.office.elevator.domain.repository.UserProfileRepository

class UserProfileRepositoryImpl : UserProfileRepository {

    override suspend fun getUser(idToken: String): UserData {
        return UserData()
    }

    override suspend fun pushUser(userData: UserData): Boolean {
        return true
    }
}