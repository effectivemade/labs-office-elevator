package band.effective.office.tv.screen.duolingo

import band.effective.office.tv.domain.autoplay.model.AutoplayState
import band.effective.office.tv.domain.autoplay.model.NavigateRequests
import band.effective.office.tv.domain.model.duolingo.DuolingoUser
import band.effective.office.tv.screen.navigation.Screen

data class DuolingoUIState(
    val users: List<DuolingoUser> = listOf(),
    val error: String = "",
    val keySort: KeySortDuolingoUser = KeySortDuolingoUser.Xp,
    override val isLoading: Boolean = true,
    override val isData: Boolean = false,
    override val isError: Boolean = false,
    override val screenName: Screen = Screen.Duolingo,
    override var navigateRequest: NavigateRequests = NavigateRequests.Nowhere
): AutoplayState

enum class KeySortDuolingoUser {
    Xp, Streak
}