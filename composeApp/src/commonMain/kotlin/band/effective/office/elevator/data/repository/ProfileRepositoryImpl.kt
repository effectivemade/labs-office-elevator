package band.effective.office.elevator.data.repository


import band.effective.office.elevator.data.MockUsers
import band.effective.office.elevator.domain.repository.ProfileRepository
import band.effective.office.elevator.domain.models.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import org.koin.core.component.KoinComponent


class ProfileRepositoryImpl: ProfileRepository, KoinComponent {

    override suspend fun updateUser(user: User) {
        val index = MockUsers.mutableListUsers.indices.find { MockUsers.mutableListUsers[it].id == user.id }?:0
            MockUsers.mutableListUsers.apply {
            this[index] = user
        }
    }
    override suspend fun getUser(id: String): Flow<User>  = flow{
        MockUsers.mutableListUsers.apply {
            emit(this.find { it.id == id } ?: this[0]) }
    }
}