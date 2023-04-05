package band.effective.office.tv.domain

import band.effective.office.tv.domain.models.Employee.EmployeeInfo

sealed class LatestEventInfoUiState {
    data class Success(var employeeInfos: List<EmployeeInfo>) : LatestEventInfoUiState()
    data class Error(var exception: Throwable?) : LatestEventInfoUiState()
}