package band.effective.office.tvServer.service.mattermost

import band.effective.office.tvServer.repository.mattermost.MattermostRepository
import band.effective.office.tvServer.repository.message.MessageRepository

class MattermostService(
    private val mattermostRepository: MattermostRepository,
    private val messageRepository: MessageRepository
) {
    suspend fun handelMessage(messageId: String, chanelId: String) {
        val message = mattermostRepository.readMessage(messageId)
        mattermostRepository.answerOnMessage(chanelId = chanelId, rootId = messageId, message = "Принято")
        messageRepository.sendMessage(message.toString())
    }
}