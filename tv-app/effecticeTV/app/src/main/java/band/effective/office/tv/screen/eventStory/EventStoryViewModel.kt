package band.effective.office.tv.screen.eventStory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import band.effective.office.tv.core.network.entity.Either
import band.effective.office.tv.core.ui.screen_with_controls.TimerSlideShow
import band.effective.office.tv.domain.autoplay.AutoplayableViewModel
import band.effective.office.tv.domain.autoplay.model.NavigateRequests
import band.effective.office.tv.domain.use_cases.EventStoryDataCombinerUseCase
import band.effective.office.tv.screen.eventStory.models.StoryModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class EventStoryViewModel @Inject constructor(
    private val eventStoryData: EventStoryDataCombinerUseCase,
    private val timer: TimerSlideShow,
) : ViewModel(), AutoplayableViewModel {
    private val mutableState = MutableStateFlow(LatestEventInfoUiState.empty)
    override val state = mutableState.asStateFlow()

    override fun switchToFirstItem() {
        mutableState.update { state -> state.copy(currentStoryIndex = 0) }
    }

    override fun switchToLastItem() {
        mutableState.update { state -> state.copy(currentStoryIndex = state.eventsInfo.size - 1) }
    }

    init {
        viewModelScope.launch {
            initDataStory()
        }
        initTimer()
        startTimer()
    }

    private suspend fun initDataStory() {
        val exceptionHandler = CoroutineExceptionHandler { _, exception ->
            updateStateAsException((exception as Error).message.orEmpty())
        }
        withContext(Dispatchers.IO + exceptionHandler) {
            eventStoryData.getAllDataForStories().collectLatest { events ->
                    when (events) {
                        is Either.Success -> updateStateAsSuccessfulFetch(events.data)

                        is Either.Failure -> updateStateAsException(events.error)
                    }
                }
        }
    }

    private fun updateStateAsException(error: String) {
        mutableState.update { state ->
            state.copy(
                isError = true,
                errorText = error,
                isLoading = false,
            )
        }
    }

    private fun updateStateAsSuccessfulFetch(events: List<StoryModel>) {
        mutableState.update { state ->
            state.copy(
                eventsInfo = events,
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
}


