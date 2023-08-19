package band.effective.office.elevator.data.database

import band.effective.office.elevator.Database
import band.effective.office.elevator.domain.models.User
import band.effective.office.elevator.scheme.ProfileData

class DBSourceImpl(
    val database: Database
) : DBSource {

    private val profileQueries = database.profileQueries
    override fun getCurrentUserInfo(): User {
        TODO("Not yet implemented")
    }

    override fun update(user: User) {
        with(user) {
            profileQueries.update(
                idToken = id,
                name = userName,
                post = post,
                email = email,
                phoneNumber = phoneNumber,
                telegramNick = telegram,
                imageUrl = imageUrl
            )
        }
    }
}