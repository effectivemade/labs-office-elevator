package band.effective.office.tv.screen.eventStory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import band.effective.office.tv.core.network.entity.Either
import band.effective.office.tv.core.ui.screen_with_controls.TimerSlideShow
import band.effective.office.tv.domain.autoplay.AutoplayableViewModel
import band.effective.office.tv.domain.autoplay.model.AutoplayState
import band.effective.office.tv.domain.autoplay.model.NavigateRequests
import band.effective.office.tv.domain.model.duolingo.DuolingoUser
import band.effective.office.tv.domain.model.message.BotMessage
import band.effective.office.tv.domain.model.message.MessageQueue
import band.effective.office.tv.domain.model.notion.EmployeeInfoEntity
import band.effective.office.tv.domain.model.notion.processEmployeeInfo
import band.effective.office.tv.network.use_cases.DuolingoManager
import band.effective.office.tv.repository.notion.EmployeeInfoRepository
import band.effective.office.tv.screen.duolingo.model.toUI
import band.effective.office.tv.screen.eventStory.models.DuolingoUserInfo
import band.effective.office.tv.screen.eventStory.models.MessageInfo
import band.effective.office.tv.screen.eventStory.models.StoryModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

private fun MessageQueue.toListOfEmployeeInfo(): List<MessageInfo> =
    this.queue.value.queue.map { MessageInfo(it) }

private fun BotMessage.toMessageInfo(): MessageInfo = MessageInfo(this)

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

    override fun switchToFirstItem(prevScreenState: AutoplayState) {
        mutableState.update { state ->
            state.copy(
                currentStoryIndex = 0,
                isPlay = prevScreenState.isPlay
            )
        }
    }

    override fun switchToLastItem(prevScreenState: AutoplayState) {
        mutableState.update { state ->
            state.copy(
                currentStoryIndex = state.eventsInfo.size - 1,
                isPlay = prevScreenState.isPlay
            )
        }
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
                    mutableState.update { it.copy(currentStoryIndex = 0) }
                }
            },
            isPlay = state.value.isPlay
        )
        checkMessage()
    }

    private fun checkMessage() = viewModelScope.launch {

        MessageQueue.secondQueue.queue.collect {
            val messages = MessageQueue.secondQueue.queue.value.queue
            val messagesInStory =
                state.value.eventsInfo.filterIsInstance<MessageInfo>().map { it.message }
            val commonMessages =
                messagesInStory.filter { messagesInStory -> messages.any { messageInQueue -> messageInQueue.id == messagesInStory.id } }
            val addMessages = (messages - commonMessages).map { it.toMessageInfo() }
            val deleteMessages = (messagesInStory - commonMessages).map { it.toMessageInfo() }
            val newEventInfo = state.value.eventsInfo + addMessages - deleteMessages
            mutableState.update {
                it.copy(
                    eventsInfo = newEventInfo,
                    currentStoryIndex = if (it.currentStoryIndex >= newEventInfo.size)
                        newEventInfo.size - 1
                    else it.currentStoryIndex
                )
            }
        }
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
                                        users = users
                                            .sortedByDescending { it.totalXp }
                                            .subList(
                                                0,
                                                if (users.size <= 10) users.size else 11
                                            ) // 11 because we show 10 users on screen
                                            .toUI(),
                                        keySort = KeySortDuolingoUser.Xp
                                    ) as StoryModel
                                    val userStreakSort = DuolingoUserInfo(
                                        users = users
                                            .sortedByDescending { it.streakDay }
                                            .subList(
                                                0,
                                                if (users.size <= 10) users.size else 11
                                            ) // 11 because we show 10 users on screen
                                            .filter { it.streakDay != 0 }
                                            .toUI(),
                                        keySort = KeySortDuolingoUser.Streak
                                    ) as StoryModel
                                    listOf(
                                        userXpSort,
                                        userStreakSort
                                    ) + MessageQueue.secondQueue.toListOfEmployeeInfo()
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
                    mutableState.update { it.copy(currentStoryIndex = 0) }
                }
            }
            is EventStoryScreenEvents.OnClickPreviousItem -> {
                if (state.value.currentStoryIndex - 1 >= 0) {
                    mutableState.update { it.copy(currentStoryIndex = it.currentStoryIndex - 1) }
                } else {
                    mutableState.update { it.copy(navigateRequest = NavigateRequests.Back) }
                    mutableState.update { it.copy(currentStoryIndex = state.value.eventsInfo.size - 1) }
                }
            }
        }
    }

    fun playStory() {
        timer.startTimer()
    }

    fun stopStory() {
        timer.stopTimer()
    }
}

