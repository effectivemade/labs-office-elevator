package band.effective.office.tv.screen.eventStory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import band.effective.office.tv.core.network.entity.Either
import band.effective.office.tv.core.ui.screen_with_controls.TimerSlideShow
import band.effective.office.tv.domain.autoplay.AutoplayableViewModel
import band.effective.office.tv.domain.autoplay.model.NavigateRequests
import band.effective.office.tv.domain.model.notion.EmployeeInfoEntity
import band.effective.office.tv.repository.notion.EmployeeInfoRepository
import band.effective.office.tv.domain.model.notion.processEmployeeInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    init {
        fetchStories()
        initTimer()
        startTimer()
    }

    private fun fetchStories() {
        val exceptionHandler = CoroutineExceptionHandler { _, exception ->
            updateStateAsException(exception as Error)
        }
        viewModelScope.launch(Dispatchers.Default + exceptionHandler) {
            repository.latestEvents().collectLatest { events ->
                when (events) {
                    is Either.Success -> {
                        updateStateAsSuccessfulFetch(events.data)
                    }
                    is Either.Failure -> {
                        updateStateAsException(Error(events.error))
                    }
                }
            }
        }
    }

    fun startTimer() {
        timer.startTimer()
    }

    fun stopTimer() {
        timer.stopTimer()
    }

    private fun initTimer() {
        timer.init(
            scope = viewModelScope,
            callbackToEnd = {
                if (isLastStory()) {
                    onLastStory()
                } else {
                    moveToNextStory()
                }
            },
            isPlay = state.value.isPlay
        )
    }

    private fun updateStateAsException(error: Error) {
        mutableState.update { state ->
            state.copy(
                isError = true,
                errorText = error.message ?: "",
                isLoading = false,
            )
        }
    }

    private fun updateStateAsSuccessfulFetch(events: List<EmployeeInfoEntity>) {
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

    fun sendEvent(event: EventStoryScreenEvents) {
        when (event) {
            is EventStoryScreenEvents.OnClickPlayButton -> {
                handlePlayState()
            }
            is EventStoryScreenEvents.OnClickNextItem -> {
                playNextStory()
            }
            is EventStoryScreenEvents.OnClickPreviousItem -> {
                playPreviousStory()
            }
        }
    }

    private fun handlePlayState() {
        timer.resetTimer()
        stopTimer()
        val isPlay = !state.value.isPlay
        mutableState.update { it.copy(isPlay = isPlay) }
        if (isPlay) timer.startTimer()
    }

    private fun playNextStory() {
        timer.resetTimer()
        if (isLastStory()) {
            onLastStory()
        } else {
            moveToNextStory()
        }
    }

    private fun playPreviousStory() {
        timer.resetTimer()
        if (isFirstStory()) {
            onFirstStory()
        } else {
            moveToPreviousStory()
        }
    }

    private fun moveToNextStory() =
        mutableState.update { it.copy(currentStoryIndex = it.currentStoryIndex + 1) }

    private fun moveToPreviousStory() =
        mutableState.update { it.copy(currentStoryIndex = it.currentStoryIndex - 1) }

    private fun isLastStory() = state.value.currentStoryIndex + 1 == state.value.eventsInfo.size

    private fun isFirstStory() = state.value.currentStoryIndex == 0

    private fun onLastStory() {
        mutableState.update { it.copy(navigateRequest = NavigateRequests.Forward) }
        mutableState.update { it.copy(currentStoryIndex = 0) }
    }

    private fun onFirstStory() {
        mutableState.update { it.copy(navigateRequest = NavigateRequests.Back) }
        mutableState.update { it.copy(currentStoryIndex = state.value.eventsInfo.size - 1) }
    }


}
