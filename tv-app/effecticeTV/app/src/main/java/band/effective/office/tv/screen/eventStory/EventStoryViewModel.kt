package band.effective.office.tv.screen.eventStory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import band.effective.office.tv.core.network.entity.Either
import band.effective.office.tv.core.ui.screen_with_controls.TimerSlideShow
import band.effective.office.tv.domain.autoplay.AutoplayableViewModel
import band.effective.office.tv.domain.autoplay.model.NavigateRequests
import band.effective.office.tv.domain.model.duolingo.DuolingoUser
import band.effective.office.tv.domain.model.notion.EmployeeInfoEntity
import band.effective.office.tv.domain.model.notion.EmployeeInfoRepository
import band.effective.office.tv.domain.model.notion.processEmployeeInfo
import band.effective.office.tv.network.use_cases.DuolingoManager
import band.effective.office.tv.screen.eventStory.models.DuolingoUserInfo
import band.effective.office.tv.screen.eventStory.models.StoryModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class EventStoryViewModel @Inject constructor(
    private val repository: EmployeeInfoRepository,
    private val timer: TimerSlideShow,
    private val duolingo: DuolingoManager
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

        viewModelScope.launch {
            initDataStory()
        }
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
    }


    private suspend fun initDataStory() {
        withContext(ioDispatcher) {
            repository.latestEvents.combine(duolingo.getDuolingoUserinfo()) { employeeInfoEntities: List<EmployeeInfoEntity>, usersDuolingo: Either<String, List<DuolingoUser>> ->
                when (usersDuolingo) {
                    is Either.Success -> {
                        employeeInfoEntities.processEmployeeInfo() +
                            run {
                                val users = usersDuolingo.data
                                val userXpSort = DuolingoUserInfo(
                                    users = users.sortedByDescending { it.totalXp },
                                    keySort = KeySortDuolingoUser.Xp
                                    ) as StoryModel
                                val userStreakSort = DuolingoUserInfo(
                                    users = users.sortedByDescending { it.streakDay },
                                    keySort = KeySortDuolingoUser.Streak
                                    ) as StoryModel
                                listOf(userXpSort, userStreakSort)
                            }
                    }

                    is Either.Failure -> {
                        employeeInfoEntities.processEmployeeInfo()
                    }
                }
            }.catch { exception ->
                mutableState.update { state ->
                    state.copy(
                        isError = true,
                        errorText = exception.message ?: "",
                        isLoading = false,
                    )
                }
            }.collectLatest { events ->
                mutableState.update { oldState ->
                    oldState.copy(
                        isLoading = false,
                        isData = true,
                        isError = false,
                        eventsInfo = events,
                        currentStoryIndex = 0,
                        isPlay = true
                    )
                }
                timer.startTimer()
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

