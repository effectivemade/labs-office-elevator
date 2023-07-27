package band.effective.office.elevator.ui.profile.domain

import band.effective.office.elevator.ui.profile.domain.models.User

interface ProfileRepository {
    suspend fun getUser(id:String): User
}