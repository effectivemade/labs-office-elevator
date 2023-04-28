package band.effective.office.tv.domain.model.message

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class MessageQueue {
    val queue: MutableStateFlow<UpdatingQueue> = MutableStateFlow(UpdatingQueue())
    fun push(message: BotMessage) = queue.update { it.push(message = message) }
    fun pop() = queue.update { it.pop() }
    fun top(): BotMessage = queue.value.top()
    fun isNotEmpty() = queue.value.queue.isNotEmpty()
    fun removeMessage(message: BotMessage) = queue.update { it.copy(queue = it.queue - message) }
    fun message(id: String) = queue.value.queue.firstOrNull { it.id == id }

    companion object {
        val firstQueue = MessageQueue()
        val secondQueue = MessageQueue()
    }
}