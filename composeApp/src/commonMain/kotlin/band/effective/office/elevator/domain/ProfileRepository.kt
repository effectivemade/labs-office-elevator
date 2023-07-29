package band.effective.office.elevator.domain

import band.effective.office.elevator.domain.models.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job

interface ProfileRepository {
    suspend fun getUser(id:String): User
    suspend fun updateUser(user: User)
}