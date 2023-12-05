package band.effective.office.tv.screen.autoplay

import band.effective.office.tv.screen.navigation.Screen

data class AutoplayUiState(
    val isLoading: Boolean,
    val isLoaded: Boolean,
    val isError: Boolean,
    val currentScreen: Screen
) {
    companion object {
        val defaultState = AutoplayUiState(
            isLoading = true,
            isLoaded = false,
            isError = false,
            currentScreen = Screen.Autoplay
        )
    }
}