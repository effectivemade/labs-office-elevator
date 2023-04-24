package band.effective.office.tv.domain.botLogic

import band.effective.office.tv.domain.botLogic.model.BotEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow

interface MessengerBot {
    fun start(scope: CoroutineScope)
}