package band.effective.office.tv.domain.botLogic.impl

import band.effective.office.tv.domain.botLogic.MessengerBot
import band.effective.office.tv.domain.botLogic.model.BotEvent
import band.effective.office.tv.domain.model.message.BotMessage
import band.effective.office.tv.domain.model.message.MessageQueue
import band.effective.office.tv.network.mattermost.mattermostWebSocketClient.MattermostWebSocketClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class MattermostBot @Inject constructor(private val client: MattermostWebSocketClient) :
    MessengerBot {
    override fun start(scope: CoroutineScope) {
        client.connect()
        client.subscribe { event ->
            when (event){
                is BotEvent.PostMessage -> {
                    MessageQueue.push(BotMessage(event.message, GregorianCalendar()))
                    scope.launch { client.postMessage(
                        channelId = event.channelId,
                        message = event.toString(),
                        root = if (event.rootId == "") event.messageId else event.rootId
                    ) }
                }
                is BotEvent.Reaction -> {
                    scope.launch { client.postMessage(
                        channelId = event.channelId,
                        message = event.toString(),
                        root = event.messageId
                    ) }
                }
            }
        }
    }
}