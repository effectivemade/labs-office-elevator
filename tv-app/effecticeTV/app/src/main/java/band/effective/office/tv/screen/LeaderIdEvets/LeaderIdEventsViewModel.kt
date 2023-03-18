package band.effective.office.tv.screen.LeaderIdEvets

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import band.effective.office.tv.repository.leaderIdRepository.LeaderIdEventsInfoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
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
        leaderIdEventsInfoRepository.getEventsInfo().collect{ event ->
            if(event.id == -1) mutableState.update { it.copy(isError = true,eventsInfo = it.eventsInfo + event) }
            else mutableState.update { it.copy(isLoad = true, eventsInfo = it.eventsInfo + event) }
        }
    }
}