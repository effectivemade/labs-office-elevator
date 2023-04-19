package band.effective.office.tv.screen.message

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import band.effective.office.tv.domain.botLogic.MessengerBot
import dagger.hilt.android.lifecycle.HiltViewModel
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
        bot.start()
        collectEvent()
    }

    private fun collectEvent() = viewModelScope.launch {
        bot.eventsList.collect { events ->
            mutableState.update { events.lastOrNull()?.message ?: "empty" }
        }
    }
}