package band.effective.office.elevator.data

import band.effective.office.elevator.domain.UserRepository
import band.effective.office.elevator.domain.models.User

class UserRepositoryImpl : UserRepository{

    private val mutableListSession = mutableListOf(
        User(
        id = "1", imageUrl = "pry.jpg", userName = "Ivanov Ivan", post = "Android-developer",
        telegram = "@fldf", phoneNumber = "+7-950-211-32-43", email = "fgfg@effectiveband"
        ) ,
        User(
            id = "2", imageUrl = "oii.jpg", userName = "Petrov Ivan", post = "Android-developer",
            telegram = "@kjhf", phoneNumber = "+7-950-211-32-43", email = "ghfgh@effectiveband"
        ),
        User(
            id = "3", imageUrl = "ghh.jpg", userName = "Ivanov Petr", post = "Android-developer",
            telegram = "@fgds", phoneNumber = "+7-950-211-32-43", email = "mnmgu@effectiveband"
        )
    )

    override suspend fun getLastUserId(): String {
       return mutableListSession[mutableListSession.lastIndex].id
    }
}