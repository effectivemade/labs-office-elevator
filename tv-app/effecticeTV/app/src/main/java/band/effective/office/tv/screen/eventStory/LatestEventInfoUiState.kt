package band.effective.office.tv.screen.eventStory

import band.effective.office.tv.domain.models.Employee.EmployeeInfo

data class LatestEventInfoUiState(
    val isError: Boolean,
    val errorText: String,
    val stackTrace: String,
    val isLoading: Boolean,
    val isLoaded: Boolean,
    val eventsInfo: MutableList<EmployeeInfo>,
    var currentStoryIndex: Int,
) {
    companion object {
        val empty = LatestEventInfoUiState(
            isError = false,
            errorText = "",
            stackTrace = "",
            isLoading = true,
            isLoaded = false,
            eventsInfo = mutableListOf(),
            currentStoryIndex = -1,
        )
    }
}