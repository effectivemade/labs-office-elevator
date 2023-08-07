package band.effective.office.elevator.data.database

import band.effective.office.elevator.domain.models.User

interface DBSource {
    fun getIdToken(): String

    fun getName(): String

    fun getPost(): String

    fun getEmail(): String

    fun getPhoneNumber(): String

    fun getTelegramNick(): String

    fun getImageUrl(): String?

    fun getUser(): User

    fun updateToken(idToken: String)

    fun updateName(name: String, idToken: String)

    fun updatePost(post: String, idToken: String)

    fun updateEmail(email: String, idToken: String)

    fun updatePhoneNumber(phoneNumber: String, idToken: String)

    fun updateTelegramNick(telegramNick: String, idToken: String)

    fun updateImageUrl(imageUrl: String, idToken: String)

    fun insertUser(user: User)
}