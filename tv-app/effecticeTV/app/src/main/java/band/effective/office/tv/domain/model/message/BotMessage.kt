package band.effective.office.tv.domain.model.message

import java.util.*

data class BotMessage(
    val channelId: String,
    val id: String,
    val text: String,
    val finish: GregorianCalendar,
    val rootId: String
) {
    companion object {
        val emptyMessage =
            BotMessage(
                channelId = "",
                id = "",
                text = "",
                finish = GregorianCalendar(),
                rootId = ""
            )
        val deletedMessage: MutableList<BotMessage> = mutableListOf()
        fun safeMessage(message: BotMessage){
            deletedMessage.add(message)
            if (deletedMessage.size > 10){
                deletedMessage.removeAt(0)
            }
        }
    }
}
