package band.effective.office.tv.network.mattermost.mattermostWebSocketClient

import band.effective.office.tv.domain.botLogic.model.BotEvent

interface MattermostWebSocketClient {
    fun connect()
    fun disconnect()
    fun subscribe(handler: (BotEvent) -> Unit)
}