package band.effective.office.elevator.domain

import band.effective.office.elevator.domain.models.User
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    suspend fun getUser(id:String): Flow<User>
    suspend fun updateUser(user: User)
}