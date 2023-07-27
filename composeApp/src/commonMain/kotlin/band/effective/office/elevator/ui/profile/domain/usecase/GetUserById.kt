package band.effective.office.elevator.ui.profile.domain.usecase

import band.effective.office.elevator.ui.profile.data.ProfileRepositoryImpl
import kotlinx.coroutines.flow.flow

class GetUserById(private val repository: ProfileRepositoryImpl) {
    operator fun invoke(id:String) = flow{
        val response = repository.getUser(id)
        emit(response)
    }
}