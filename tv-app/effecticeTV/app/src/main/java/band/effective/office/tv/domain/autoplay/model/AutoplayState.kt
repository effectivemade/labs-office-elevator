package band.effective.office.tv.domain.autoplay.model

import band.effective.office.tv.screen.navigation.Screen

interface AutoplayState {
    val isLoading: Boolean
    val isData: Boolean
    val isError: Boolean
    val screenName: Screen
    var navigateRequest: NavigateRequests
}