package band.effective.office.tv.screen.message.secondaryMessage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import band.effective.office.tv.core.ui.screen_with_controls.TimerSlideShow
import band.effective.office.tv.domain.autoplay.AutoplayableViewModel
import band.effective.office.tv.domain.autoplay.model.NavigateRequests
import band.effective.office.tv.domain.botLogic.BotConfig
import band.effective.office.tv.domain.model.message.MessageQueue
import band.effective.office.tv.repository.uselessFactRepository.UselessFactRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SecondaryMessageViewModel @Inject constructor(
    private val uselessFactRepository: UselessFactRepository,
    private val timer: TimerSlideShow
) : ViewModel(), AutoplayableViewModel {
    private var mutableState = MutableStateFlow(SecondaryMessageState.empty)
    override val state = mutableState.asStateFlow()
    override fun switchToFirstItem() {
        getUselessFact()
        mutableState.update { it.copy(currentIndex = 0) }
    }

    override fun switchToLastItem() {
        getUselessFact()
        mutableState.update { it.copy(currentIndex = it.messageList.size - 1) }
    }

    init {
        updateMessageList()
        timer.init(
            scope = viewModelScope,
            callbackToEnd = {
                if (state.value.currentIndex + 1 < state.value.messageList.size) {
                    mutableState.update { it.copy(currentIndex = it.currentIndex + 1) }
                } else {
                    mutableState.update {
                        it.copy(
                            navigateRequest = NavigateRequests.Forward,
                            currentIndex = 0
                        )
                    }
                }
            },
            isPlay = state.value.isPlay,
            period = BotConfig.commonMessageDelay
        )
        timer.startTimer()
        //getUselessFact() //NOTE(Maksim Mishenko) speak about when call this screen
    }

    private fun getUselessFact() = viewModelScope.launch {
        mutableState.update { it.copy(uselessFact = uselessFactRepository.fact()) }
    }

    private fun updateMessageList() = viewModelScope.launch {
        MessageQueue.secondQueue.queue.collect {
            if (MessageQueue.secondQueue.isNotEmpty()) {
                mutableState.update {
                    it.copy(
                        isData = true,
                        messageList = it.messageList + MessageQueue.secondQueue.top(),
                        uselessFact = uselessFactRepository.fact()
                    )
                }
                MessageQueue.secondQueue.pop()
            }
        }
    }

    fun onEvent(event: SecondaryMessageScreenEvents) {
        timer.stopTimer()
        when (event) {
            is SecondaryMessageScreenEvents.OnClickNextButton -> {
                if (state.value.currentIndex + 1 < state.value.messageList.size) {
                    mutableState.update { it.copy(currentIndex = it.currentIndex + 1) }
                } else {
                    mutableState.update {
                        it.copy(
                            currentIndex = 0,
                            navigateRequest = NavigateRequests.Forward
                        )
                    }
                }
                timer.startTimer()
            }
            is SecondaryMessageScreenEvents.OnClickPrevButton -> {
                if (state.value.currentIndex == 0) {
                    mutableState.update {
                        it.copy(
                            currentIndex = it.messageList.size - 1,
                            navigateRequest = NavigateRequests.Back
                        )
                    }
                } else {
                    mutableState.update { it.copy(currentIndex = it.currentIndex - 1) }
                }
                timer.startTimer()
            }
            is SecondaryMessageScreenEvents.OnClickPlayButton -> {
                if (!state.value.isPlay){
                    timer.startTimer()
                }
                mutableState.update { it.copy(isPlay = !it.isPlay) }
            }
        }
    }
}