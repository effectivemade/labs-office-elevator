package band.effective.office.tv.screen.LeaderIdEvets

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
class LeaderIdEventsViewModel @Inject constructor (val leaderIdEventsInfoRepository: LeaderIdEventsInfoRepository): ViewModel() {
    private var mutableState = MutableStateFlow(LeaderIdEventsUiState.empty)
    val state = mutableState.asStateFlow()
    init {
        load()
    }
    fun load() = viewModelScope.launch {
        mutableState.update { it.copy(true,leaderIdEventsInfoRepository.getEventsInfo()) }
    }
}