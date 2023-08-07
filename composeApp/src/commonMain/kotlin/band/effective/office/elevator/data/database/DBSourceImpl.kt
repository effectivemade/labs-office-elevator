package band.effective.office.elevator.data.database

import band.effective.office.elevator.Database
import band.effective.office.elevator.domain.models.User

class DBSourceImpl(
    val database: Database
) : DBSource {

    private val profileQueries = database.profileQueries

    override fun getIdToken(): String = profileQueries.selectIdToken().executeAsOne()

    override fun getName(): String = profileQueries.selectName().executeAsOne()

    override fun getPost(): String = profileQueries.selectPost().executeAsOne()

    override fun getEmail(): String = profileQueries.selectEmail().executeAsOne()

    override fun getPhoneNumber(): String = profileQueries.selectPhoneNumber().executeAsOne()

    override fun getTelegramNick(): String = profileQueries.selectTelegramNick().executeAsOne()

    override fun getImageUrl(): String = profileQueries.selectImageUrl().executeAsOne()

    override fun getUser(): User =
        with(profileQueries.selectAll().executeAsOne()) {
            User(
                id = idToken,
                phoneNumber = phoneNumber,
                post = post,
                userName = name,
                telegram = telegramNick,
                email = email,
                imageUrl = imageUrl
            )
        }

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

    override fun insertUser(user: User) {
        with(user) {
            profileQueries.insert(
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