package band.effective.office.elevator.domain

import band.effective.office.elevator.domain.models.User

interface ProfileRepository {
    suspend fun getUser(id:String): User
    suspend fun updateUser(user: User)
}