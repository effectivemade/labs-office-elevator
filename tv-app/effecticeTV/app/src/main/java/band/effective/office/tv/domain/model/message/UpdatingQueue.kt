package band.effective.office.tv.domain.model.message

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

data class UpdatingQueue(val queue: List<BotMessage> = listOf()){
    fun push(message: BotMessage): UpdatingQueue = copy(queue = queue + message)
    fun top(): BotMessage = queue.first()
    fun pop(): UpdatingQueue = copy(queue = queue - top())
}
