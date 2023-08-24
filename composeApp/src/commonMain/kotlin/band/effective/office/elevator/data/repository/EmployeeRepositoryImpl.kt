package band.effective.office.elevator.data.repository

import band.effective.office.elevator.data.MockUsers
import band.effective.office.elevator.domain.models.EmployeeInfo
import band.effective.office.elevator.domain.repository.EmployeeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class EmployeeRepositoryImpl: EmployeeRepository{
    object EmployeesData {
        val initial=listOf(
            EmployeeInfo.defaultEmployee)
    }
    override suspend fun getEmployeesInfo(): Flow<List<EmployeeInfo>> {
        return flow<List<EmployeeInfo>> {
            emit(
                MockUsers.mutableListEmployee
            )

        }

    }

}
