package band.effective.office.tv.screen.leaderIdEvents

import band.effective.office.tv.domain.autoplay.model.AutoplayState
import band.effective.office.tv.domain.autoplay.model.NavigateRequests
import band.effective.office.tv.domain.model.leaderId.LeaderIdEventInfo
import band.effective.office.tv.screen.navigation.Screen

data class LeaderIdEventsUiState(
    override val isLoading: Boolean,
    override val isData: Boolean,
    override val isError: Boolean,
    val eventsInfo: List<LeaderIdEventInfo>,
    val curentEvent: Int,
    override val isPlay: Boolean,
    val errorText: String,
    override val screenName: Screen = Screen.Events,
    override var navigateRequest: NavigateRequests = NavigateRequests.Nowhere
) : AutoplayState {
    companion object {
        val empty = LeaderIdEventsUiState(
            isLoading = true,
            isData = false,
            isError = false,
            eventsInfo = listOf(),
            curentEvent = -1,
            isPlay = false,
            errorText = ""
        )
    }
}