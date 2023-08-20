package office.effective.common.notifications

import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import io.ktor.http.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import office.effective.dto.WorkspaceDTO

class NotificationSender(private val fcm: FirebaseMessaging) {

    fun sendMessage(topic: String, httpMethod: HttpMethod, message: WorkspaceDTO) {
        val json = Json.encodeToString(message)
        val msg: Message = Message.builder()
            .setTopic(topic)
            .putData("action", httpMethod.toString())
            .putData("object", json)
            .build()
        fcm.send(msg)
    }
}