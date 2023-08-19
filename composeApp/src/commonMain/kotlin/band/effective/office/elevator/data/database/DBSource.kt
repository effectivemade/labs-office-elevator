package band.effective.office.elevator.data.database

import band.effective.office.elevator.domain.models.User

interface DBSource {
    fun getCurrentUserInfo(): User

    fun update(user: User)
}