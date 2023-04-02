package band.effective.office.tv.screen.leaderIdEvents

import band.effective.office.tv.domain.model.LeaderIdEventInfo

data class LeaderIdEventsUiState(
    val isLoad: Boolean,
    val isError: Boolean,
    val eventsInfo: List<LeaderIdEventInfo>,
    val curentEvent: Int,
    val isPlay: Boolean,
    val errorText: String
) {
    companion object {
        val empty = LeaderIdEventsUiState(
            isLoad = false,
            isError = false,
            eventsInfo = listOf(),
            curentEvent = -1,
            isPlay = false,
            errorText = ""
        )
    }
}