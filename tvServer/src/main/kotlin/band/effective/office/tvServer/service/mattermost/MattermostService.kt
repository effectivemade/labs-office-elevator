package band.effective.office.tvServer.service.mattermost

import band.effective.office.tvServer.model.MattermostMessage
import band.effective.office.tvServer.model.SavedMessage
import band.effective.office.tvServer.repository.mattermost.MattermostRepository
import band.effective.office.tvServer.repository.message.MessageRepository
import band.effective.office.tvServer.repository.post.PostRepository
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.toList
import kotlinx.serialization.json.Json
import java.time.LocalDateTime

class MattermostService(
    private val mattermostRepository: MattermostRepository,
    private val messageRepository: MessageRepository,
    private val postRepository: PostRepository
) {
    suspend fun handelMessage(messageId: String, chanelId: String) {
        val message = mattermostRepository.readMessage(messageId)
        mattermostRepository.answerOnMessage(chanelId = chanelId, rootId = messageId, message = "Принято")
        messageRepository.sendMessage(Json.encodeToString(MattermostMessage.serializer(), message))
    }

    suspend fun getPosts(): List<SavedMessage> {
        return postRepository.getPosts()
            .filter { message ->
                val now = LocalDateTime.now()
                now > message.start && now < message.finish//TODO(Maksim Mishenko) fix after migrate
                (now < message.finish).apply {
                    if (!this) postRepository.deletePost(message.message.id)
                }
            }.toList()
    }

    suspend fun savePost(message: SavedMessage) {
        val now = LocalDateTime.now()
        if (now < message.finish)
            postRepository.addPost(message)
    }

    suspend fun updatePost(message: SavedMessage) {
        val now = LocalDateTime.now()
        if (now < message.finish)
            postRepository.updatePost(message)
    }

    suspend fun deletePost(id: String) {
        postRepository.deletePost(id)
    }
}