package band.effective.office.elevator.data.repository

import band.effective.office.elevator.domain.models.UserData
import band.effective.office.elevator.domain.repository.UserProfileRepository

class UserProfileRepositoryImpl : UserProfileRepository {
    override suspend fun getUser(idToken: String): UserData {
        return if (idToken.isNotEmpty())
            UserData(
                phoneNumber = "0000000000",
                post = "Android developer",
                email = "@test.ru",
                name = "Вася Вася",
                imageUrl = null
            )
        else
            UserData(
                phoneNumber = "",
                post = "",
                email = "",
                name = "",
                imageUrl = null
            )
    }
}