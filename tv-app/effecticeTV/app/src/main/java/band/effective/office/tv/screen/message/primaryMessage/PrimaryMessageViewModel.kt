package band.effective.office.tv.screen.message.primaryMessage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import band.effective.office.tv.core.ui.screen_with_controls.TimerSlideShow
import band.effective.office.tv.domain.botLogic.BotConfig
import band.effective.office.tv.domain.botLogic.MessengerBot
import band.effective.office.tv.domain.model.message.MessageQueue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PrimaryMessageViewModel @Inject constructor(
    private val bot: MessengerBot,
    private val timer: TimerSlideShow
) :
    ViewModel() {
    private val mutableState = MutableStateFlow(PrimaryMessageScreenState.empty)
    val state = mutableState.asStateFlow()

    init {
        viewModelScope.launch() { bot.start(viewModelScope) }
        timer.init(
            scope = viewModelScope,
            callbackToEnd = {
                if (state.value.currentMessage + 1 < state.value.messagesList.size) {
                    mutableState.update { it.copy(currentMessage = it.currentMessage + 1) }
                } else {
                    mutableState.update { PrimaryMessageScreenState.empty }
                }
            },
            isPlay = true,
            period = BotConfig.importantMessageDelay
        )
        collectEvent()
    }

    private fun collectEvent() = viewModelScope.launch {
        val firstQueue = MessageQueue.firstQueue
        firstQueue.queue.collect {
            if (firstQueue.isNotEmpty()) {
                mutableState.update {
                    it.copy(
                        isEmpty = false,
                        messagesList = it.messagesList + firstQueue.top()
                    )
                }
                firstQueue.pop()
                timer.startTimer()
            }
        }
    }
}