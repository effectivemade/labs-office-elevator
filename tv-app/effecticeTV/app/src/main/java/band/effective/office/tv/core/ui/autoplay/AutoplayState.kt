package band.effective.office.tv.core.ui.autoplay

import band.effective.office.tv.screen.navigation.Screen

enum class NavigateRequests{
    Nowhere, Forward, Back
}

interface AutoplayState {
    val isLoading: Boolean
    val isLoaded: Boolean
    val isError: Boolean
    val screenName: Screen
    var navigateRequest: NavigateRequests
}