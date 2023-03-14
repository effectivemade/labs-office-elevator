package band.effective.office.tv.view

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import band.effective.office.tv.network.leader.LeaderApi
import band.effective.office.tv.network.leader.models.SearchEvent.SearchEventsResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor (val api: LeaderApi): ViewModel() {
    private var mutableApiResponse = MutableStateFlow<SearchEventsResponse?>((null))
    val apiResponse = mutableApiResponse.asStateFlow()
    init {
        viewModelScope.launch(Dispatchers.IO) {
            val tst = api.eventInfo(407500).execute().body()
            Log.i("my test", tst!!.data.full_name);
            mutableApiResponse.update { api.searchEvents(10,893,3942).execute().body() }
        }
    }
}