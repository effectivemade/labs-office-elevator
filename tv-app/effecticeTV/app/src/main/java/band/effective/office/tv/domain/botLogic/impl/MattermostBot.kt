package band.effective.office.tv.domain.botLogic.impl

import band.effective.office.tv.domain.botLogic.MessengerBot
import band.effective.office.tv.domain.botLogic.model.BotEvent
import band.effective.office.tv.network.mattermost.mattermostWebSocketClient.MattermostWebSocketClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class MattermostBot @Inject constructor(private val client: MattermostWebSocketClient) :
    MessengerBot {
    private var mutableEventsList = MutableStateFlow(listOf<BotEvent>())
    override val eventsList = mutableEventsList.asStateFlow()

    override fun start() {
        client.connect()
        client.subscribe { event ->
            mutableEventsList.update { it + event }
        }
    }
}