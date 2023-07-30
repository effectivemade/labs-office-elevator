package band.effective.office.elevator.data

import band.effective.office.elevator.domain.models.User
import kotlinx.coroutines.flow.Flow

class MockProfileData {
    private val mutableListUser = mutableListOf(
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

     fun updateUser(user: User) {
        val index = mutableListUser.indices.find { mutableListUser[it].id == user.id }?:0
        mutableListUser[index] = user
    }

      fun getUser(id: String): User {
        return mutableListUser.find{it.id == id}?: mutableListUser[0]
    }
}