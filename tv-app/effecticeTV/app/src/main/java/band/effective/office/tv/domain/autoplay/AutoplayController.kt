package band.effective.office.tv.domain.autoplay

import band.effective.office.tv.domain.autoplay.model.AutoplayState
import band.effective.office.tv.domain.autoplay.model.ScreenState
import band.effective.office.tv.screen.navigation.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AutoplayController {
    private var mutableState: MutableStateFlow<AutoplayState> =
        MutableStateFlow(AutoplayState.default)
    val state = mutableState.asStateFlow()
    private var error = false


    fun registerScreen(screen: Screen) {
        mutableState.update { it.copy(screensList = it.screensList + screen) }
        checkData()
    }

    private fun checkData() {
        mutableState.update {
            when {
                error -> it.copy(isError = true, isLoading = false)
                else -> it.copy(isData = true, isLoading = false)
            }
        }
    }

    fun addError(screen: Screen) {
        mutableState.update {
            it.copy(
                screensList = it.screensList - screen,
                currentScreenNumber = if (it.currentScreenNumber >= it.screensList.size) it.screensList.size - 1 else it.currentScreenNumber
            )
        }
        if (state.value.screensList.size == 0) error = true
        checkData()
    }

    fun nextScreen(screenState: ScreenState) {
        if (state.value.screensList.isEmpty()) return
        mutableState.update {
            it.copy(
                currentScreenNumber = (it.currentScreenNumber + 1) % it.screensList.size,
                screenState = screenState
            )
        }
    }

    fun prevScreen(screenState: ScreenState) {
        if (state.value.screensList.isEmpty()) return
        mutableState.update {
            it.copy(
                currentScreenNumber = (it.currentScreenNumber + it.screensList.size - 1) % it.screensList.size,
                screenState = screenState
            )
        }
    }

    fun resetController() {
        mutableState.update { AutoplayState.default }
        error = false
    }
}