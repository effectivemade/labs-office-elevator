package band.effective.office.tv.domain.model.message

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

data class MessageQueue(val queue: List<BotMessage> = listOf()){
    fun push(message: BotMessage): MessageQueue = copy(queue = queue + message)
    fun top(): BotMessage = queue.first()
    fun pop(): MessageQueue = copy(queue = queue - top())

    companion object{
        val queue = MutableStateFlow(MessageQueue())
        fun push(message: BotMessage) = queue.update { it.push(message = message) }
        fun pop() = queue.update { it.pop() }
        fun top(): BotMessage = queue.value.top()
    }
}
