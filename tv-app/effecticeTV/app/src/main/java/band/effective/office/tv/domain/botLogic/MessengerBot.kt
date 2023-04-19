package band.effective.office.tv.domain.botLogic

import band.effective.office.tv.domain.botLogic.model.BotEvent
import kotlinx.coroutines.flow.StateFlow

interface MessengerBot {
    val eventsList: StateFlow<List<BotEvent>>
    fun start()
}