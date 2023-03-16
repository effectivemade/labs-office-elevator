package band.effective.office.tv.screen.LeaderIdEvets

import band.effective.office.tv.model.LeaderIdEventInfo

sealed class LeaderIdEventsUiState {
    class Loading: LeaderIdEventsUiState()
    class Load(
        val EventsList: List<LeaderIdEventInfo>
    ): LeaderIdEventsUiState()
}