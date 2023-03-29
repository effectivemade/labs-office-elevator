package band.effective.office.tv.screen.leaderIdEvents

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import band.effective.office.tv.repository.leaderIdRepository.LeaderIdEventsInfoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LeaderIdEventsViewModel @Inject constructor(val leaderIdEventsInfoRepository: LeaderIdEventsInfoRepository) :
    ViewModel() {
    private var mutableState = MutableStateFlow(LeaderIdEventsUiState.empty)
    val state = mutableState.asStateFlow()

    init {
        load()
    }

    fun load() = viewModelScope.launch {
        leaderIdEventsInfoRepository.getEventsInfo(100).collect { event ->
            if (event.id == -1) mutableState.update {
                it.copy(isError = true, eventsInfo = it.eventsInfo + event)
            }
            else mutableState.update { it.copy(isLoad = true, eventsInfo = it.eventsInfo + event, curentEvent = 0) }
        }
    }
    fun sendEvent(event: LeaderIdScreenEvents){
        when(event){
            is LeaderIdScreenEvents.OnClickPlayButton -> {
                mutableState.update { it.copy(isPlay = !it.isPlay) }
            }
            is LeaderIdScreenEvents.OnClickNextItem -> {
                if (state.value.curentEvent + 1 < state.value.eventsInfo.size){
                    mutableState.update { it.copy(curentEvent = it.curentEvent + 1) }
                }
            }
            is LeaderIdScreenEvents.OnClickPreviousItem -> {
                if (state.value.curentEvent - 1 > 0){
                    mutableState.update { it.copy(curentEvent = it.curentEvent - 1) }
                }
            }
        }
    }
}