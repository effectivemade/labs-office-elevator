package band.effective.office.elevator.domain.repository

import band.effective.office.elevator.domain.models.EmployeeInfo
import kotlinx.coroutines.flow.StateFlow

interface EmployeeRepository {
    suspend fun getEmployeesInfo(): StateFlow<List<EmployeeInfo>>

}