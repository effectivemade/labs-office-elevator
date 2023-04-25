package band.effective.office.tv.screen.message

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import band.effective.office.tv.domain.botLogic.MessengerBot
import band.effective.office.tv.domain.model.message.MessageQueue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MessageScreenViewModel @Inject constructor(private val bot: MessengerBot) :
    ViewModel() {
    private val mutableState = MutableStateFlow("")
    val state = mutableState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) { bot.start(viewModelScope) }
        collectEvent()
    }

    private fun collectEvent() = viewModelScope.launch {
        val firstQueue = MessageQueue.firstQueue
        firstQueue.queue.collect {
            if (firstQueue.isNotEmpty()) {
                mutableState.update { firstQueue.top().text }
                delay(2000)
                firstQueue.pop()
            }
        }
    }
}