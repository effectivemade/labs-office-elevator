package band.effective.office.elevator.domain.repository

import band.effective.office.elevator.domain.models.EmployeeInfo
import kotlinx.coroutines.flow.Flow

interface EmployeeRepository {
    suspend fun getEmployeesInfo(): Flow<List<EmployeeInfo>>

}