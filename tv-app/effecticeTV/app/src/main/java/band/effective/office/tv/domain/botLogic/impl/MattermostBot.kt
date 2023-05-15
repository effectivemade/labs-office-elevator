package band.effective.office.tv.domain.botLogic.impl

import band.effective.office.tv.domain.botLogic.BotConfig
import band.effective.office.tv.domain.botLogic.MessengerBot
import band.effective.office.tv.domain.botLogic.model.BotEvent
import band.effective.office.tv.domain.model.message.BotMessage
import band.effective.office.tv.domain.model.message.MessageQueue
import band.effective.office.tv.domain.model.message.toBotMessage
import band.effective.office.tv.network.mattermost.mattermostWebSocketClient.MattermostWebSocketClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class MattermostBot @Inject constructor(private val client: MattermostWebSocketClient) :
    MessengerBot {
    private lateinit var scope: CoroutineScope
    /**Start mattermost bot
     * @param scope scope for bot*/
    override fun start(scope: CoroutineScope) {
        this.scope = scope
        scope.launch { client.connect() }
        client.subscribe { event ->
            when (event) {
                is BotEvent.PostMessage -> {
                    if (event.message.isForBot()) {
                        val message = event.toBotMessage().filter()
                        if (message.text.length > BotConfig.maxMessageLength){
                            message.answer("Слишком много букавок")
                            return@subscribe
                        }
                        MessageQueue.secondQueue.push(message)
                        message.answer("Сообщение принято")
                    }
                }
                is BotEvent.Reaction -> {
                    when (event.emojiName) {
                        BotConfig.importantMessageReaction -> {
                            if (MessageQueue.firstQueue.contain(event.messageId)){
                                return@subscribe
                            }
                            val message = MessageQueue.secondQueue.message(event.messageId)
                                ?: BotMessage.deletedMessage.firstOrNull() { it.id == event.messageId }
                            if (message != null) {
                                MessageQueue.secondQueue.removeMessage(message)
                                MessageQueue.firstQueue.push(message)
                                message.answer("Приоритет повышен")
                            }
                        }
                        BotConfig.deleteMessageReaction -> {
                            val message = MessageQueue.secondQueue.message(event.messageId)
                                ?: MessageQueue.firstQueue.message(event.messageId) ?: BotMessage.deletedMessage.firstOrNull {it.id == event.messageId}
                            if (message != null && message.author.id == event.userId) {
                                MessageQueue.secondQueue.removeMessage(message)
                                MessageQueue.firstQueue.removeMessage(message)
                                BotMessage.deletedMessage.remove(message)
                                message.answer("Сообщение удалено")
                            }
                        }
                    }
                }
            }
        }
    }
    /**Send message in chat
     * @param message chanelId, text and thread for post, other message's fields no matter*/
    private fun postMessage(message: BotMessage) = scope.launch {
        client.postMessage(
            channelId = message.channelId,
            message = message.text,
            root = message.rootId
        )
    }
    /**Check that it's message for bot*/
    private fun String.isForBot(): Boolean = contains(client.name())

    /**Send answer on message*/
    private fun BotMessage.answer(text: String) = postMessage(copy(text = text))

    /**Filter message's text from useless information*/
    private fun BotMessage.filter() = copy(
        text = text.replace(
            "@${client.name()} ",
            ""
        )
    )
}
