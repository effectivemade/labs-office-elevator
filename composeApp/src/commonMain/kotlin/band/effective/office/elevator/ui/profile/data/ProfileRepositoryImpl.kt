package band.effective.office.elevator.ui.profile.data

import band.effective.office.elevator.ui.profile.domain.ProfileRepository
import band.effective.office.elevator.ui.profile.domain.models.User

class ProfileRepositoryImpl : ProfileRepository{
    override suspend fun getUser(id: String): User {
        return User(id = "1",imageUrl = "pry.jpg",userName = "Ivanov Ivan",post ="Android-developer",
        telegram = "@fldf",phoneNumber = "89502113243" ,email = "fgfg@effectiveband")
    }
}