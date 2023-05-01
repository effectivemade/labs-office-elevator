package band.effective.office.tv.screen.eventStory

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import band.effective.office.tv.core.ui.screen_with_controls.TimerSlideShow
import band.effective.office.tv.domain.autoplay.AutoplayableViewModel
import band.effective.office.tv.domain.autoplay.model.NavigateRequests
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
class EventStoryViewModel @Inject constructor(
    private val repository: EmployeeInfoRepository,
    private val timer: TimerSlideShow
) :
    ViewModel(), AutoplayableViewModel {
    private val mutableState =
        MutableStateFlow(LatestEventInfoUiState.empty)
    override val state = mutableState.asStateFlow()

    override fun switchToFirstItem() {
        mutableState.update { state -> state.copy(currentStoryIndex = 0) }
    }

    override fun switchToLastItem() {
        mutableState.update { state -> state.copy(currentStoryIndex = state.eventsInfo.size - 1) }
    }

    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

    init {
        fetchBirthdays()
        timer.init(
            scope = viewModelScope,
            callbackToEnd = {
                if (state.value.currentStoryIndex + 1 < state.value.eventsInfo.size) {
                    mutableState.update { it.copy(currentStoryIndex = it.currentStoryIndex + 1) }
                } else {
                    mutableState.update { it.copy(navigateRequest = NavigateRequests.Forward) }
                }
            },
            isPlay = state.value.isPlay
        )
        timer.startTimer()
    }

    private fun fetchBirthdays() {
        viewModelScope.launch {
            withContext(ioDispatcher) {
                repository.latestEvents.catch { exception ->
                    mutableState.update { state ->
                        state.copy(
                            isError = true,
                            errorText = exception.message ?: "",
                            isLoading = false,
                        )
                    }
                }.collectLatest { events ->
                    val resultList = events.processEmployeeInfo()
                    mutableState.update { state ->
                        state.copy(
                            eventsInfo = resultList,
                            currentStoryIndex = 0,
                            isLoading = false,
                            isData = true,
                            isPlay = true,
                        )
                    }
                }
            }
        }
    }

    fun sendEvent(event: EventStoryScreenEvents) {
        timer.resetTimer()
        when (event) {
            is EventStoryScreenEvents.OnClickPlayButton -> {
                timer.stopTimer()
                val isPlay = !state.value.isPlay
                mutableState.update { it.copy(isPlay = isPlay) }
                if (isPlay) timer.startTimer()
            }
            is EventStoryScreenEvents.OnClickNextItem -> {
                if (state.value.currentStoryIndex + 1 < state.value.eventsInfo.size) {
                    mutableState.update { it.copy(currentStoryIndex = it.currentStoryIndex + 1) }
                } else {
                    mutableState.update { it.copy(navigateRequest = NavigateRequests.Forward) }
                }
            }
            is EventStoryScreenEvents.OnClickPreviousItem -> {
                if (state.value.currentStoryIndex - 1 >= 0) {
                    mutableState.update { it.copy(currentStoryIndex = it.currentStoryIndex - 1) }
                } else {
                    mutableState.update { it.copy(navigateRequest = NavigateRequests.Back) }
                }
            }
        }
    }
}

