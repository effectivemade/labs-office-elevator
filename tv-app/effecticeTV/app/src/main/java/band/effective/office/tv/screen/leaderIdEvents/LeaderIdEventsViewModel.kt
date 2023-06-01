package band.effective.office.tv.screen.leaderIdEvents

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import band.effective.office.tv.core.network.Either
import band.effective.office.tv.core.ui.screen_with_controls.TimerSlideShow
import band.effective.office.tv.domain.autoplay.AutoplayController
import band.effective.office.tv.domain.autoplay.model.ScreenState
import band.effective.office.tv.repository.leaderId.LeaderIdEventsInfoRepository
import band.effective.office.tv.screen.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class LeaderIdEventsViewModel @Inject constructor(
    private val leaderIdEventsInfoRepository: LeaderIdEventsInfoRepository,
    private val timer: TimerSlideShow,
    private val autoplayController: AutoplayController
) : ViewModel() {
    private var mutableState = MutableStateFlow(LeaderIdEventsUiState.empty)
    val state = mutableState.asStateFlow()

    val finish = GregorianCalendar()

    init {
        finish.set(Calendar.MONTH, GregorianCalendar().get(Calendar.MONTH) + 1)
        load()
        timer.init(
            scope = viewModelScope, callbackToEnd = {
                if (state.value.curentEvent + 1 < state.value.eventsInfo.size) {
                    mutableState.update { it.copy(curentEvent = it.curentEvent + 1) }
                } else {
                    timer.stopTimer()
                    autoplayController.nextScreen(state.value.toScreenState(true))
                }
            }, isPlay = state.value.isPlay
        )
        if (state.value.isPlay)
            timer.startTimer()
        checkAutoplayState()
    }

    private fun checkAutoplayState() = viewModelScope.launch {
        autoplayController.state.collect { controllerState ->
            if (controllerState.screensList.isEmpty()) return@collect
            if (controllerState.screensList[controllerState.currentScreenNumber] == Screen.Events) {
                mutableState.update {
                    it.copy(
                        isPlay = autoplayController.state.value.screenState.isPlay,
                        curentEvent = if (autoplayController.state.value.screenState.isForwardDirection) 0 else it.eventsInfo.size - 1
                    )
                }
                timer.startTimer()
            } else {
                timer.stopTimer()
            }
        }
    }

    fun load() = viewModelScope.launch {
        leaderIdEventsInfoRepository.getEventsInfo(finish).collect { either ->
            when {
                either is Either.Failure -> mutableState.update {
                    autoplayController.addError(Screen.Events)
                    it.copy(
                        isLoading = false,
                        isError = true,
                        errorText = it.errorText + "${either.error}\n"
                    )
                }
                either is Either.Success && !state.value.isData -> mutableState.update {
                    it.copy(
                        isLoading = false,
                        isData = true,
                        eventsInfo = it.eventsInfo + either.data,
                        curentEvent = if (autoplayController.state.value.screenState.isForwardDirection) 0 else (it.eventsInfo + either.data).size - 1,
                        isPlay = true
                    )
                }
                either is Either.Success -> mutableState.update {
                    it.copy(
                        eventsInfo = it.eventsInfo + either.data
                    )
                }
            }
        }
    }

    fun sendEvent(event: LeaderIdScreenEvents) {
        timer.resetTimer()
        when (event) {
            is LeaderIdScreenEvents.OnClickPlayButton -> {
                timer.stopTimer()
                val isPlay = !state.value.isPlay
                mutableState.update { it.copy(isPlay = isPlay) }
                if (isPlay)
                    timer.startTimer()
            }
            is LeaderIdScreenEvents.OnClickNextItem -> {
                if (state.value.curentEvent + 1 < state.value.eventsInfo.size) {
                    mutableState.update { it.copy(curentEvent = it.curentEvent + 1) }
                } else {
                    mutableState.update { it.copy(curentEvent = 0) }
                    autoplayController.nextScreen(state.value.toScreenState(true))
                }
            }
            is LeaderIdScreenEvents.OnClickPreviousItem -> {
                if (state.value.curentEvent - 1 >= 0) {
                    mutableState.update { it.copy(curentEvent = it.curentEvent - 1) }
                } else {
                    mutableState.update { it.copy(curentEvent = it.eventsInfo.size - 1) }
                    autoplayController.prevScreen(state.value.toScreenState(false))
                }
            }
        }
    }

    private fun LeaderIdEventsUiState.toScreenState(direction: Boolean): ScreenState =
        ScreenState(isPlay = isPlay, isForwardDirection = direction)
}



