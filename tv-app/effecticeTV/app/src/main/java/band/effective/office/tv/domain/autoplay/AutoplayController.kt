package band.effective.office.tv.domain.autoplay

import band.effective.office.tv.core.ui.autoplay.NavigateRequests
import band.effective.office.tv.screen.autoplay.ScreenDescription
import band.effective.office.tv.screen.navigation.Screen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

enum class ScreenState {
    Data, Error, Load
}

class AutoplayController {
    private var scope: CoroutineScope? = null
    val screensList = mutableListOf<ScreenDescription>()
    val isLoadMap = mutableMapOf<Screen, ScreenState>()
    private var mutableCurrentScreenIndex = MutableStateFlow(-1)
    val currentScreenIndex = mutableCurrentScreenIndex.asStateFlow()

    fun init(scope: CoroutineScope) {
        this.scope = scope
    }

    fun registerScreen(description: ScreenDescription) {
        screensList.add(description)
        isLoadMap[description.screenName] = ScreenState.Load
        scope?.launch {
            description.viewModel.state.collect { loadState ->
                when {
                    loadState.isLoaded -> isLoadMap[loadState.screenName] = ScreenState.Data
                    loadState.isError -> {
                        //TODO(Maksim Mishenko): repair error handler
                        isLoadMap[loadState.screenName] = ScreenState.Data
                        /*mutableCurrentScreenIndex.update {
                            screensList.indexOfFirst {
                                it.screenName == loadState.screenName
                            }
                        }*/
                    }
                    else -> isLoadMap[loadState.screenName] = ScreenState.Load
                }
                if (isLoadMap.values.all { it == ScreenState.Data } && currentScreenIndex.value == -1) mutableCurrentScreenIndex.update { 0 }
                if (loadState.navigateRequest != NavigateRequests.Nowhere) {
                    mutableCurrentScreenIndex.update { (it + if (loadState.navigateRequest == NavigateRequests.Forward) 1 else screensList.size - 1) % screensList.size }
                    loadState.navigateRequest = NavigateRequests.Nowhere
                }
            }
        }
    }
}