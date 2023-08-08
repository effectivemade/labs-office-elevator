package band.effective.office.elevator.data.database

import band.effective.office.elevator.Database
import band.effective.office.elevator.domain.models.User
import band.effective.office.elevator.scheme.ProfileData

class DBSourceImpl(
    val database: Database
) : DBSource {

    private val profileQueries = database.profileQueries

    override fun getIdToken(idToken: String): String =
        profileQueries.selectIdToken(idToken = idToken).executeAsOne()

    override fun getName(idToken: String): String =
        profileQueries.selectName(idToken = idToken).executeAsOne()

    override fun getPost(idToken: String): String =
        profileQueries.selectPost(idToken = idToken).executeAsOne()

    override fun getEmail(idToken: String): String =
        profileQueries.selectEmail(idToken = idToken).executeAsOne()

    override fun getPhoneNumber(idToken: String): String =
        profileQueries.selectPhoneNumber(idToken = idToken).executeAsOne()

    override fun getTelegramNick(idToken: String): String =
        profileQueries.selectTelegramNick(idToken = idToken).executeAsOne()

    override fun getImageUrl(idToken: String): String =
        profileQueries.selectImageUrl(idToken = idToken).executeAsOne()

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

    override fun updateToken(idToken: String) {
        profileQueries.updateToken(idToken = idToken, idToken_ = idToken)
    }

    override fun updateName(name: String, idToken: String) {
        profileQueries.updateName(name = name, idToken = idToken)
    }

    override fun updatePost(post: String, idToken: String) {
        profileQueries.updatePost(post = post, idToken = idToken)
    }

    override fun updateEmail(email: String, idToken: String) {
        profileQueries.updateEmail(email = email, idToken = idToken)
    }

    override fun updatePhoneNumber(phoneNumber: String, idToken: String) {
        profileQueries.updatePhoneNumber(phoneNumber = phoneNumber, idToken = idToken)
    }

    override fun updateTelegramNick(telegramNick: String, idToken: String) {
        profileQueries.updateTelegramNick(telegramNick = telegramNick, idToken = idToken)
    }

    override fun updateImageUrl(imageUrl: String, idToken: String) {
        profileQueries.updateImageUrl(imageUrl = imageUrl, idToken = idToken)
    }

    override fun insertUser(profileData: ProfileData) {
        with(profileData) {
            profileQueries.insert(
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