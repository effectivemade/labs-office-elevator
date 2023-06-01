package band.effective.office.tv.screen.eventStory

import band.effective.office.tv.screen.eventStory.models.StoryModel

data class LatestEventInfoUiState(
    val isError: Boolean,
    val errorText: String,
    val isLoading: Boolean,
    val isData: Boolean,
    val isPlay: Boolean,
    val eventsInfo: List<StoryModel>,
    var currentStoryIndex: Int,
    val keySort: KeySortDuolingoUser = KeySortDuolingoUser.Xp
) {
    companion object {
        val empty = LatestEventInfoUiState(
            isError = false,
            errorText = "",
            isPlay = true,
            isLoading = true,
            isData = false,
            eventsInfo = listOf(),
            currentStoryIndex = -1,
        )
    }
}

enum class KeySortDuolingoUser {
    Xp, Streak
}