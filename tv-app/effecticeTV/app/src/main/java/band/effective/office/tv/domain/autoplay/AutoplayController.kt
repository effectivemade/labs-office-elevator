package band.effective.office.tv.domain.autoplay

import band.effective.office.tv.domain.autoplay.model.AutoplayState
import band.effective.office.tv.domain.autoplay.model.NavigateRequests
import band.effective.office.tv.domain.autoplay.model.ScreenDescription
import band.effective.office.tv.domain.autoplay.model.ScreenState
import band.effective.office.tv.screen.navigation.Screen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
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
        screensList.add(description)
        mutableLoadMap.update { it.plus(Pair(description.screenName, ScreenState.Load))  }
        checkScreenState(description)
    }

    private fun checkError() = scope?.launch {
        loadMap.collect(){
            if (it.containsValue(ScreenState.Error)){
                mutableCurrentScreenIndex.update {
                    screensList.indexOfFirst {
                        it.viewModel.state.value.isError
                    }
                }
            }
        }
    }

    private fun checkData() = scope?.launch{
        loadMap.collect(){
            if (it.values.all {it == ScreenState.Data}&& currentScreenIndex.value == -1){
                mutableCurrentScreenIndex.update { 0 }
            }
        }
    }

    private fun navigate(loadState: AutoplayState){
        when (loadState.navigateRequest){
            NavigateRequests.Forward -> {
                mutableCurrentScreenIndex.update{(it+1)% screensList.size}
                screensList[currentScreenIndex.value].viewModel.switchToFirstItem()
                loadState.navigateRequest = NavigateRequests.Nowhere
            }
            NavigateRequests.Back -> {
                mutableCurrentScreenIndex.update { (it + screensList.size - 1) % screensList.size }
                screensList[currentScreenIndex.value].viewModel.switchToLastItem()
                loadState.navigateRequest = NavigateRequests.Nowhere
            }
            NavigateRequests.Nowhere -> {}
        }
    }

    private fun checkScreenState(description: ScreenDescription) = scope?.launch{
        description.viewModel.state.collect { loadState ->
            when {
                loadState.isData -> mutableLoadMap.update{
                    val mutableMap = it.toMutableMap()
                    mutableMap[loadState.screenName] = ScreenState.Data
                    mutableMap
                }
                loadState.isError -> {
                    mutableLoadMap.update{
                        val mutableMap = it.toMutableMap()
                        mutableMap[loadState.screenName] = ScreenState.Error
                        mutableMap
                    }
                }
                else -> {}
            }
            navigate(loadState)
        }
    }
}