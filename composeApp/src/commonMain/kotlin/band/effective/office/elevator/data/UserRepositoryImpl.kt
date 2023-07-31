package band.effective.office.elevator.data

import band.effective.office.elevator.domain.UserRepository
import band.effective.office.elevator.domain.models.User

class UserRepositoryImpl : UserRepository{

    private val mutableListSession = mutableListOf(
        User(
        id = "1", imageUrl = "pry.jpg", userName = "Ivanov Ivan", post = "Android-developer",
        telegram = "@fldf", phoneNumber = "89502113243", email = "fgfg@effectiveband"
        ) ,
        User(
            id = "2", imageUrl = "oii.jpg", userName = "Petrov Ivan", post = "Android-developer",
            telegram = "@kjhf", phoneNumber = "89502003243", email = "ghfgh@effectiveband"
        ),
        User(
            id = "3", imageUrl = "ghh.jpg", userName = "Ivanov Petr", post = "Android-developer",
            telegram = "@fgds", phoneNumber = "89502883243", email = "mnmgu@effectiveband"
        )
    )

    override suspend fun getLastUserId(): String {
       return mutableListSession[mutableListSession.lastIndex].id
    }
}