package band.effective.office.elevator.data.database

import band.effective.office.elevator.Database
import band.effective.office.elevator.domain.models.User
import band.effective.office.elevator.scheme.ProfileData

class DBSourceImpl(
    val database: Database
) : DBSource {

    private val profileQueries = database.profileQueries

    override fun getUser(idToken: String): ProfileData =
        with(profileQueries.selectUser(idToken = idToken).executeAsOne()) {
            ProfileData(
                idToken = idToken,
                phoneNumber = phoneNumber,
                post = post,
                name = name,
                telegramNick = telegramNick,
                email = email,
                imageUrl = imageUrl
            )
        }

    override fun getAll(): List<ProfileData> = profileQueries.selectAll().executeAsList()

    override fun update(profileData: ProfileData) {
        with(profileData) {
            profileQueries.update(
                idToken = idToken,
                name = name,
                post = post,
                email = email,
                phoneNumber = phoneNumber,
                telegramNick = telegramNick,
                imageUrl = imageUrl
            )
        }
    }
}