package band.effective.office.tv.view.uiStateModels

import band.effective.office.tv.domain.models.Employee.EmployeeInfo

open class LatestEventInfoUiState {
    data class Success(var employeeInfos: List<EmployeeInfo>) : LatestEventInfoUiState()
    data class Error(var exception: Throwable?) : LatestEventInfoUiState()
}