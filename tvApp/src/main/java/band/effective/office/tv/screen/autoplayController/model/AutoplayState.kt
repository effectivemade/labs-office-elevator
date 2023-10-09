package band.effective.office.tv.screen.autoplayController.model

import band.effective.office.tv.screen.navigation.Screen

data class AutoplayState(
    val isLoading: Boolean,
    val isData: Boolean,
    val isError: Boolean,
    val screensList: List<Screen>,
    val currentScreenNumber: Int,
    val screenState: ScreenState
) {
    companion object {
        val default = AutoplayState(
            isLoading = true,
            isData = false,
            isError = false,
            screensList = listOf(),
            currentScreenNumber = 0,
            screenState = ScreenState.default
        )
    }
}