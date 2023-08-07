package band.effective.office.elevator.data.database

import band.effective.office.elevator.scheme.ProfileData

interface DBSource {
    fun getIdToken(idToken: String): String

    fun getName(idToken: String): String

    fun getPost(idToken: String): String

    fun getEmail(idToken: String): String

    fun getPhoneNumber(idToken: String): String

    fun getTelegramNick(idToken: String): String

    fun getImageUrl(idToken: String): String?

    fun getUser(idToken: String): ProfileData

    fun getAll(): List<ProfileData>

    fun updateToken(idToken: String)

    fun updateName(name: String, idToken: String)

    fun updatePost(post: String, idToken: String)

    fun updateEmail(email: String, idToken: String)

    fun updatePhoneNumber(phoneNumber: String, idToken: String)

    fun updateTelegramNick(telegramNick: String, idToken: String)

    fun updateImageUrl(imageUrl: String, idToken: String)

    fun insertUser(profileData: ProfileData)
}