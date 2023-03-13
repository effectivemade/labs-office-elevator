package band.effective.office.tv.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import band.effective.office.tv.network.leader.LeaderApi
import band.effective.office.tv.network.leader.models.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor (val api: LeaderApi): ViewModel() {
    private var mutableApiResponse = MutableStateFlow<Response?>((null))
    val apiResponse = mutableApiResponse.asStateFlow()
    init {
        viewModelScope.launch(Dispatchers.IO) {
            mutableApiResponse.update { api.searchEvents(10, 3942).execute().body() }
        }
    }
}