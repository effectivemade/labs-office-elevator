package band.effective.office.tv.screen.leaderIdEvents

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import band.effective.office.tv.core.ui.screen_with_controls.TimerSlideShow
import band.effective.office.tv.repository.leaderIdRepository.LeaderIdEventsInfoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LeaderIdEventsViewModel @Inject constructor(
    private val leaderIdEventsInfoRepository: LeaderIdEventsInfoRepository,
    private val timer: TimerSlideShow
) : ViewModel() {
    private var mutableState = MutableStateFlow(LeaderIdEventsUiState.empty)
    val state = mutableState.asStateFlow()

    init {
        load()
        timer.init(
            scope = viewModelScope,
            callbackToEnd = {
                if (state.value.curentEvent + 1 < state.value.eventsInfo.size) {
                    mutableState.update { it.copy(curentEvent = it.curentEvent + 1) }
                }
            },
            isPlay = state.value.isPlay
        )
        timer.startTimer()
    }

    fun load() = viewModelScope.launch {
        leaderIdEventsInfoRepository.getEventsInfo(100).collect { event ->
            if (event.id == -1) mutableState.update {
                it.copy(isError = true, eventsInfo = it.eventsInfo + event)
            }
            else if (state.value.isLoad)
                mutableState.update {
                    it.copy(
                        eventsInfo = it.eventsInfo + event
                    )
                }
                else
                mutableState.update {
                    it.copy(
                        isLoad = true,
                        eventsInfo = it.eventsInfo + event,
                        curentEvent = 0,
                        isPlay = true
                    )
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
                if (isPlay) timer.startTimer()
            }
            is LeaderIdScreenEvents.OnClickNextItem -> {
                if (state.value.curentEvent + 1 < state.value.eventsInfo.size) {
                    mutableState.update { it.copy(curentEvent = it.curentEvent + 1) }
                }
            }
            is LeaderIdScreenEvents.OnClickPreviousItem -> {
                if (state.value.curentEvent - 1 >= 0) {
                    mutableState.update { it.copy(curentEvent = it.curentEvent - 1) }
                }
            }
        }
    }
}