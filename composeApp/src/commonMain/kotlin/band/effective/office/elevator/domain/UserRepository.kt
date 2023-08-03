package band.effective.office.elevator.domain

interface UserRepository {
    suspend fun getLastUserId():String
}