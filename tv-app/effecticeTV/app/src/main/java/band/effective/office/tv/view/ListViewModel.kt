package band.effective.office.tv.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import band.effective.office.tv.model.LeaderIdEventInfo
import band.effective.office.tv.network.leader.LeaderApi
import band.effective.office.tv.network.leader.models.searchEvent.SearchEventsResponse
import band.effective.office.tv.repository.leaderIdRepository.impl.LeaderIdEventsInfoRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor (val api: LeaderApi): ViewModel() {
    private var mutableApiResponse = MutableStateFlow<List<LeaderIdEventInfo>>(listOf())
    val apiResponse = mutableApiResponse.asStateFlow()
    init {
        viewModelScope.launch(Dispatchers.IO) {
            mutableApiResponse.update { LeaderIdEventsInfoRepositoryImpl(api).getEventsInfo() }
        }
    }
}