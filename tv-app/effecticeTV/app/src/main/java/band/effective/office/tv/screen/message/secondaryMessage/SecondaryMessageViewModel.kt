package band.effective.office.tv.screen.message.secondaryMessage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import band.effective.office.tv.domain.autoplay.AutoplayableViewModel
import band.effective.office.tv.domain.autoplay.model.NavigateRequests
import band.effective.office.tv.domain.model.message.MessageQueue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SecondaryMessageViewModel @Inject constructor() : ViewModel(), AutoplayableViewModel {
    private var mutableState = MutableStateFlow(SecondaryMessageState.empty)
    override val state = mutableState.asStateFlow()
    override fun switchToFirstItem() {
        //mutableState.update { SecondaryMessageState.empty  }
    }

    override fun switchToLastItem() {
        //mutableState.update { SecondaryMessageState.empty  }
    }

    init {
        updateMessageList()
    }

    private fun updateMessageList() = viewModelScope.launch {
        MessageQueue.secondQueue.queue.collect{
            if (MessageQueue.secondQueue.isNotEmpty()){
                mutableState.update {
                    it.copy(
                        isData = true,
                        messageList = it.messageList + MessageQueue.secondQueue.top().text,
                        navigateRequest = NavigateRequests.Nowhere
                    )
                }
                MessageQueue.secondQueue.pop()
            }
            else{
                delay(10000)
                mutableState.update { it.copy(navigateRequest = NavigateRequests.Forward) }
            }
        }
    }
}