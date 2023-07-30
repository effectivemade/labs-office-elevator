package band.effective.office.elevator.data


import band.effective.office.elevator.domain.ProfileRepository
import band.effective.office.elevator.domain.models.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class ProfileRepositoryImpl : ProfileRepository, KoinComponent {

    private val mockProfileData by inject<MockProfileData>()

    override suspend fun updateUser(user: User) {
       mockProfileData.updateUser(user)
    }

    override suspend fun getUser(id: String):Flow<User> = flow{
        val user =  mockProfileData.getUser(id)
        emit(user)
    }
}