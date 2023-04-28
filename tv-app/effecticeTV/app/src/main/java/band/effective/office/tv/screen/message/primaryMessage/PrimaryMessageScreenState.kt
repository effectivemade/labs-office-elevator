package band.effective.office.tv.screen.message.primaryMessage

import band.effective.office.tv.domain.model.message.BotMessage

data class PrimaryMessageScreenState(
    val isEmpty: Boolean,
    val currentMessage: BotMessage
) {
    companion object {
        val empty = PrimaryMessageScreenState(
            isEmpty = true,
            currentMessage = BotMessage.emptyMessage
        )
    }
}
