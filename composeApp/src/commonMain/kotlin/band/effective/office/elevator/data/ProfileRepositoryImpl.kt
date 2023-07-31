package band.effective.office.elevator.data


import band.effective.office.elevator.domain.ProfileRepository
import band.effective.office.elevator.domain.models.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import org.koin.core.component.KoinComponent


class ProfileRepositoryImpl: ProfileRepository, KoinComponent {

    private val mutableListUser = MutableStateFlow(
        mutableListOf(
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
    )

    override suspend fun updateUser(user: User) {
        val index = mutableListUser.value.indices.find { mutableListUser.value[it].id == user.id }?:0
        mutableListUser.update {
            mutableListUser.value.apply {
                this[index] = user
            }
        }
    }
    override suspend fun getUser(id: String): Flow<User>  = flow{
        mutableListUser.value.apply {
            emit(this.find { it.id == id } ?: this[0]) }
    }
}