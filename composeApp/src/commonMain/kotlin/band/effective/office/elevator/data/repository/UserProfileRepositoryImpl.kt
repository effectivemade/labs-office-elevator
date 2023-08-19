package band.effective.office.elevator.data.repository

import band.effective.office.elevator.data.database.DBSource
import band.effective.office.elevator.data.database.DBSourceImpl
import band.effective.office.elevator.domain.models.UserData
import band.effective.office.elevator.domain.repository.UserProfileRepository
import band.effective.office.elevator.expects.showToast
import band.effective.office.elevator.scheme.ProfileData
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class UserProfileRepositoryImpl : UserProfileRepository, KoinComponent {

    private val dbSourceImpl by inject<DBSource>()

    override suspend fun getUser(idToken: String): UserData {

        val profileData = dbSourceImpl.getUser(idToken)

        return with(profileData) {
            UserData(
                phoneNumber, post, name, telegramNick, email, imageUrl, idToken
            )
        }
    }

    override suspend fun pushUser(userData: UserData): Boolean {

        val profileData = ProfileData(
            idToken = userData.idToken,
            name = userData.name,
            post = userData.post!!,
            email = userData.email,
            phoneNumber = userData.phoneNumber,
            telegramNick = userData.telegramNick,
            imageUrl = userData.imageUrl!!
        )

        dbSourceImpl.update(profileData)

        return true
    }
}