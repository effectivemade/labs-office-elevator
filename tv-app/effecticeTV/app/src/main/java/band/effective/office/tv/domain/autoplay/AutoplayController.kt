package band.effective.office.tv.domain.autoplay

import android.util.Log
import band.effective.office.tv.domain.autoplay.model.NavigateRequests
import band.effective.office.tv.domain.autoplay.model.ScreenDescription
import band.effective.office.tv.domain.autoplay.model.ScreenState
import band.effective.office.tv.screen.navigation.Screen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AutoplayController {
    private var scope: CoroutineScope? = null
    val screensList = mutableListOf<ScreenDescription>()
    private var mutableLoadMap = MutableStateFlow(mapOf<Screen, ScreenState>())
    val loadMap = mutableLoadMap.asStateFlow()
    private var mutableCurrentScreenIndex = MutableStateFlow(-1)
    val currentScreenIndex = mutableCurrentScreenIndex.asStateFlow()

    fun init(scope: CoroutineScope) {
        this.scope = scope
        checkError()
        checkData()
    }

    fun registerScreen(description: ScreenDescription) {
        if (screensList.any({it.screenName == description.screenName})) return
        if (screensList.isEmpty()) description.viewModel.state.value.navigateRequest = NavigateRequests.First
        screensList.add(description)
        mutableLoadMap.update { it.plus(Pair(description.screenName, ScreenState.Load)) }
        checkScreenState(description)
        description.viewModel.stopTimer()
    }

    private fun checkError() = scope?.launch {
        loadMap.collect() {
            if (it.containsValue(ScreenState.Error)) {
                mutableCurrentScreenIndex.update {
                    screensList.indexOfFirst {
                        it.viewModel.state.value.isError
                    }
                }
            }
        }
    }

    private fun checkData() = scope?.launch {
        loadMap.collect() {
            if (it.isNotEmpty() && it.values.all { it == ScreenState.Data } && currentScreenIndex.value == -1) {
                mutableCurrentScreenIndex.update { 0 }
            }
        }
    }

    private fun navigate(description: ScreenDescription) {
        val stateValue = description.viewModel.state.value
        if (stateValue.navigateRequest != NavigateRequests.Nowhere) description.viewModel.stopTimer()

        when (stateValue.navigateRequest) {
            NavigateRequests.Forward -> {
                val newIndex = (mutableCurrentScreenIndex.value + 1) % screensList.size
                screensList[newIndex].viewModel.switchToFirstItem(stateValue)
                mutableCurrentScreenIndex.update { newIndex }
                description.viewModel.state.value.navigateRequest = NavigateRequests.Nowhere
                Log.i(
                    "Autoplay Controller",
                    "Navigate forward to ${screensList[currentScreenIndex.value].screenName.name}"
                )
            }
            NavigateRequests.Back -> {
                val newIndex = (mutableCurrentScreenIndex.value + screensList.size - 1) % screensList.size
                screensList[newIndex].viewModel.switchToLastItem(stateValue)
                mutableCurrentScreenIndex.update { newIndex }
                description.viewModel.state.value.navigateRequest = NavigateRequests.Nowhere
                Log.i(
                    "Autoplay Controller",
                    "Navigate back to ${screensList[currentScreenIndex.value].screenName.name}"
                )
            }
            NavigateRequests.Nowhere -> {}
            NavigateRequests.First->{
                description.viewModel.startTimer()
                description.viewModel.state.value.navigateRequest = NavigateRequests.Nowhere
            }
        }
    }

    private fun checkScreenState(description: ScreenDescription) = scope?.launch {
        description.viewModel.state.collect { loadState ->
            when {
                loadState.isData -> mutableLoadMap.update {
                    val mutableMap = it.toMutableMap()
                    mutableMap[loadState.screenName] = ScreenState.Data
                    mutableMap
                }
                loadState.isError -> {
                    mutableLoadMap.update {
                        val mutableMap = it.toMutableMap()
                        mutableMap[loadState.screenName] = ScreenState.Error
                        mutableMap
                    }
                }
            }
            if (currentScreenIndex.value != -1) navigate(description)
        }
    }
}