package band.effective.office.tv.screen.eventStory

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import band.effective.office.tv.domain.models.Employee.*
import band.effective.office.tv.domain.models.Employee.EmployeeInfoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class EventStoryViewModel @Inject constructor(private val repository: EmployeeInfoRepository) :
    ViewModel() {
    private val _uiState =
        MutableStateFlow(LatestEventInfoUiState.empty)
    val uiState = _uiState.asStateFlow()

    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
    private val timer = object : CountDownTimer(Long.MAX_VALUE, 5000) {
        override fun onTick(millisUntilFinished: Long) {
            _uiState.update { state ->
                Log.d("EventStoryViewModel", "timer ticks - ${state.currentStoryIndex}")
                state.copy(
                    currentStoryIndex = state.currentStoryIndex + 1,
                    isLoading = false,
                    isLoaded = true,
                )
            }
            if (_uiState.value.eventsInfo.size - 1 == _uiState.value.currentStoryIndex) cancel()
        }

        override fun onFinish() {
        }
    }

    init {
        fetchBirthdays()
    }

    private fun fetchBirthdays() {
        viewModelScope.launch {
            withContext(ioDispatcher) {
                repository.latestEvents.catch { exception ->
                    _uiState.update { state ->
                        state.copy(
                            isError = true,
                            errorText = exception.message ?: "",
                            isLoading = false,
                            isLoaded = true
                        )
                    }
                }.collectLatest { events ->
                    val resultList = events.processEmployeeInfo()
                    _uiState.update { state ->
                        state.copy(
                            eventsInfo = resultList,
                            isLoading = false,
                            isLoaded = true
                        )
                    }
                    switchStory()
                }
            }
        }
    }

    private fun switchStory() {
        timer.start()
    }

}

