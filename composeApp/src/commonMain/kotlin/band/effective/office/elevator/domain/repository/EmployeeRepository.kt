package band.effective.office.elevator.domain.repository

import band.effective.office.elevator.domain.models.EmployeeInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job

interface EmployeeRepository {
    suspend fun getEmployeesInfo(employeeInfo: EmployeeInfo): List<EmployeeInfo>

    fun subscribeOnEmployeesInfoUpdates(scope: CoroutineScope, handler: (EmployeeInfo) -> Unit): Job
}