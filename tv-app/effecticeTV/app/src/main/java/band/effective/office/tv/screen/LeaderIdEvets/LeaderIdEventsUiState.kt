package band.effective.office.tv.screen.LeaderIdEvets

import band.effective.office.tv.model.LeaderIdEventInfo

data class LeaderIdEventsUiState(
    val isLoad: Boolean,
    val eventsInfo: List<LeaderIdEventInfo>
){
    companion object{
        val empty = LeaderIdEventsUiState(false, listOf())
    }
}