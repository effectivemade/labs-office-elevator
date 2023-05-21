package band.effective.office.tv.screen.eventStory

import band.effective.office.tv.domain.autoplay.model.AutoplayState
import band.effective.office.tv.domain.autoplay.model.NavigateRequests
import band.effective.office.tv.screen.eventStory.models.StoryModel
import band.effective.office.tv.screen.navigation.Screen

data class LatestEventInfoUiState(
    override val isError: Boolean,
    val errorText: String,
    override val isLoading: Boolean,
    override val isData: Boolean,
    override val isPlay: Boolean,
    val eventsInfo: List<StoryModel>,
    var currentStoryIndex: Int,
    val keySort: KeySortDuolingoUser = KeySortDuolingoUser.Xp,
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
            eventsInfo = mutableListOf(),
            currentStoryIndex = -1,
        )
    }
}

enum class KeySortDuolingoUser {
    Xp, Streak
}