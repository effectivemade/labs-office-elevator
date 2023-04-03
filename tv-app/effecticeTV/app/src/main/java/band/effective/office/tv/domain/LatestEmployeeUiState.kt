package band.effective.office.tv.domain

import band.effective.office.tv.domain.models.Employee.EmployeeInfo

sealed class LatestEmployeeUiState {
    data class Success(var employeeInfos: List<EmployeeInfo>) : LatestEmployeeUiState()
    data class Error(var exception: Throwable?) : LatestEmployeeUiState()
}