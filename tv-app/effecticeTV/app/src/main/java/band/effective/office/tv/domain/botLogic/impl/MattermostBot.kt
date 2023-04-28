package band.effective.office.tv.domain.botLogic.impl

import band.effective.office.tv.domain.botLogic.MessengerBot
import band.effective.office.tv.domain.botLogic.model.BotEvent
import band.effective.office.tv.domain.model.message.BotMessage
import band.effective.office.tv.domain.model.message.MessageQueue
import band.effective.office.tv.network.mattermost.mattermostWebSocketClient.MattermostWebSocketClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class MattermostBot @Inject constructor(private val client: MattermostWebSocketClient) :
    MessengerBot {
    override fun start(scope: CoroutineScope) {
        scope.launch { client.connect() }
        client.subscribe { event ->
            when (event) {
                is BotEvent.PostMessage -> {
                    if (event.message.contains(client.name())) {
                        val message = event.toBotMessage()
                        MessageQueue.secondQueue.push(
                            message.copy(
                                text = event.message.replace(
                                    "@${client.name()} ",
                                    ""
                                )
                            )
                        )
                        postMessage(scope, message.copy(text = "Сообщение принято"))
                    }

                }
                is BotEvent.Reaction -> {
                    when (event.emojiName) {
                        "warning" -> {
                            val message = MessageQueue.secondQueue.message(event.messageId)
                                ?: BotMessage.deletedMessage.first { it.id == event.messageId }
                            if (message != null) {
                                MessageQueue.secondQueue.removeMessage(message)
                                MessageQueue.firstQueue.push(message)
                                postMessage(scope, message.copy(text = "Приоритет повышен"))
                            }
                        }
                        "no_entry_sign" -> {
                            val message = MessageQueue.secondQueue.message(event.messageId)
                                ?: MessageQueue.firstQueue.message(event.messageId)
                            if (message != null) {
                                MessageQueue.secondQueue.removeMessage(message)
                                MessageQueue.firstQueue.removeMessage(message)
                                postMessage(scope, message.copy(text = "Сообщение удалено"))
                            }
                        }
                    }

                }
            }
        }
    }

    private fun postMessage(scope: CoroutineScope, message: BotMessage) = scope.launch {
        client.postMessage(
            channelId = message.channelId,
            message = message.text,
            root = message.rootId
        )
    }
}

private fun BotEvent.PostMessage.toBotMessage(): BotMessage =
    BotMessage(
        channelId,
        messageId,
        message,
        GregorianCalendar(),
        if (rootId == "") messageId else rootId
    )
