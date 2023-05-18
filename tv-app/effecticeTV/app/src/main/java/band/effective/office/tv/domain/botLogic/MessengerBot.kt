package band.effective.office.tv.domain.botLogic

import band.effective.office.tv.domain.botLogic.model.BotEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow

interface MessengerBot {
    /**Start mattermost bot
     * @param scope CoroutineScope for bot*/
    fun start(scope: CoroutineScope)
}