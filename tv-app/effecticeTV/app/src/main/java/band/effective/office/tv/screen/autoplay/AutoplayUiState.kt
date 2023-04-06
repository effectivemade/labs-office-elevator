package band.effective.office.tv.screen.autoplay

import band.effective.office.tv.screen.navigation.Screen

data class AutoplayUiState(
    val isLoading: Boolean,
    val isLoaded: Boolean,
    val isError: Boolean,
    val screensList: List<Screen>,
    val errorScreen: Screen?,
    val currentScreen: Int
) {
    companion object {
        val defaultState = AutoplayUiState(
            isLoading = false,
            isLoaded = false,
            isError = false,
            screensList = listOf(),
            errorScreen = null,
            currentScreen = -1
        )
    }
}