package band.effective.office.tv.screen.eventStory

import band.effective.office.tv.domain.models.Employee.EmployeeInfo

data class LatestEventInfoUiState(
    val isError: Boolean,
    val errorText: String,
    val isLoading: Boolean,
    val isLoaded: Boolean,
    val eventsInfo: MutableList<EmployeeInfo>,
) {
    companion object {
        val empty = LatestEventInfoUiState(
            isError = false,
            errorText = "",
            isLoading = true,
            isLoaded = false,
            eventsInfo = mutableListOf(),
        )
    }
}