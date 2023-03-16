package band.effective.office.tv.screen.LeaderIdEvets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import band.effective.office.tv.repository.leaderIdRepository.LeaderIdEventsInfoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LeaderIdEventsViewModel @Inject constructor (val leaderIdEventsInfoRepository: LeaderIdEventsInfoRepository): ViewModel() {
    private var mutableUiState = MutableStateFlow<LeaderIdEventsUiState>(LeaderIdEventsUiState.Loading())
    val uiState = mutableUiState.asStateFlow()
    init {
        viewModelScope.launch(Dispatchers.IO) {
            mutableUiState.update {
                LeaderIdEventsUiState.Load(leaderIdEventsInfoRepository.getEventsInfo())
            }
        }
    }
}