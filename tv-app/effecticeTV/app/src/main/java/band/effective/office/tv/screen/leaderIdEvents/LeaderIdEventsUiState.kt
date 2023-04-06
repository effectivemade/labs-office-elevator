package band.effective.office.tv.screen.leaderIdEvents

import band.effective.office.tv.core.ui.autoplay.AutoplayState
import band.effective.office.tv.core.ui.autoplay.NavigateRequests
import band.effective.office.tv.domain.model.leaderId.LeaderIdEventInfo
import band.effective.office.tv.screen.navigation.Screen

data class LeaderIdEventsUiState(
    override val isLoading: Boolean,
    override val isLoaded: Boolean,
    override val isError: Boolean,
    val eventsInfo: List<LeaderIdEventInfo>,
    val curentEvent: Int,
    val isPlay: Boolean,
    val errorText: String,
    override val screenName: Screen = Screen.Events,
    override var navigateRequest: NavigateRequests = NavigateRequests.Nowhere
) : AutoplayState {
    companion object {
        val empty = LeaderIdEventsUiState(
            isLoading = true,
            isLoaded = false,
            isError = false,
            eventsInfo = listOf(),
            curentEvent = -1,
            isPlay = false,
            errorText = ""
        )
    }
}