package band.effective.office.tvServer.service.mattermost

import band.effective.office.tvServer.repository.mattermost.MattermostRepository

class MattermostService(private val mattermostRepository: MattermostRepository) {
    suspend fun handelMessage(messageId: String, chanelId: String) {
        val message = mattermostRepository.readMessage(messageId)
        mattermostRepository.writeMessage(chanelId, message.toString())
    }
}