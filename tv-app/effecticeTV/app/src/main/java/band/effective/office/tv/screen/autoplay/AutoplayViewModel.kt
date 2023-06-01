package band.effective.office.tv.screen.autoplay

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import band.effective.office.tv.domain.autoplay.AutoplayController
import band.effective.office.tv.domain.autoplay.model.AutoplayState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AutoplayViewModel @Inject constructor(val autoplayController: AutoplayController) :
    ViewModel() {
    private var mutableState = MutableStateFlow(AutoplayUiState.defaultState)
    val state = mutableState.asStateFlow()

    init {
        load()
    }

    fun load() = viewModelScope.launch {
        autoplayController.state.collect {
            mutableState.update { autoplayController.state.value.toUiState() }
        }
    }

    private fun AutoplayState.toUiState() = AutoplayUiState(
        isLoading = isLoading,
        isLoaded = isData,
        isError = isError,
        currentScreen = screensList[currentScreenNumber]
    )
}