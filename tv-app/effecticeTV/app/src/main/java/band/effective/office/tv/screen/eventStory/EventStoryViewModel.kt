package band.effective.office.tv.screen.eventStory

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import band.effective.office.tv.domain.models.Employee.*
import band.effective.office.tv.network.use_cases.EmployeeInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class EventStoryViewModel @Inject constructor(private var employeeInfoUseCase: EmployeeInfoUseCase) :
    ViewModel() {

    private val _uiState =
        MutableStateFlow(LatestEventInfoUiState.empty)
    val uiState: StateFlow<LatestEventInfoUiState> = _uiState

    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
    private val timer = object : CountDownTimer(Long.MAX_VALUE, 5000) {
        override fun onTick(millisUntilFinished: Long) {
            _uiState.update {
                it.copy(
                    currentStoryIndex = ++it.currentStoryIndex,
                    isLoading = false,
                    isLoaded = true,
                )
            }
            Log.d("EventStoryViewModel", "Current story index" + _uiState.value.currentStoryIndex)
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
                employeeInfoUseCase.getLatestBirthdays().catch { exception ->
                    _uiState.update {
                        it.copy(
                            isError = true,
                            errorText = exception.message ?: "",
                            stackTrace = exception.stackTraceToString(),
                            isLoading = false,
                            isLoaded = true
                        )
                    }
                }.collectLatest { events ->
                    val resultList = processEmployeeInfo(events)
                    _uiState.update {
                        it.copy(
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

