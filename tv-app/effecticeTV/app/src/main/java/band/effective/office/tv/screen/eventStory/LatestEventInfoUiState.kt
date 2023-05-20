package band.effective.office.tv.screen.eventStory

import band.effective.office.tv.domain.autoplay.model.AutoplayState
import band.effective.office.tv.domain.autoplay.model.NavigateRequests
import band.effective.office.tv.domain.model.notion.EmployeeInfo
import band.effective.office.tv.screen.navigation.Screen

data class LatestEventInfoUiState(
    override val isError: Boolean,
    val errorText: String,
    override val isLoading: Boolean,
    override val isData: Boolean,
    val isPlay: Boolean,
    val eventsInfo: List<EmployeeInfo>,
    var currentStoryIndex: Int,
    override val screenName: Screen = Screen.Stories,
    override var navigateRequest: NavigateRequests = NavigateRequests.Nowhere,
) : AutoplayState {
    companion object {
        val empty = LatestEventInfoUiState(
            isError = false,
            errorText = "",
            isPlay = false,
            isLoading = true,
            isData = false,
            eventsInfo = listOf(),
            currentStoryIndex = -1,
        )
    }
}