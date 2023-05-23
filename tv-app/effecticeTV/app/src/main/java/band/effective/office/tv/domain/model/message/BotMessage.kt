package band.effective.office.tv.domain.model.message

import java.util.*

data class BotMessage(
    val channelId: String,
    val id: String,
    val text: String,
    val finish: GregorianCalendar,
    val rootId: String
)