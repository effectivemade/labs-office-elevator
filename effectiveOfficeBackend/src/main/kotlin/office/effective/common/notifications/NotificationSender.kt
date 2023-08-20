package office.effective.common.notifications

import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import io.ktor.http.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import office.effective.dto.BookingDTO
import office.effective.dto.UserDTO
import office.effective.dto.WorkspaceDTO

class NotificationSender(private val fcm: FirebaseMessaging) {

    /**
     * Sends an FCM message about workspace modification
     *
     * @param action will be put as "action" in message data
     * @param modifiedWorkspace will be put as "object" in message data
     *
     * @author Daniil Zavyalov
     */
    fun sendMessage(action: HttpMethod, modifiedWorkspace: WorkspaceDTO) {
        val json = Json.encodeToString(modifiedWorkspace)
        val msg: Message = Message.builder()
            .setTopic("workspace")
            .putData("action", action.toString())
            .putData("object", json)
            .build()
        fcm.send(msg)
    }

    /**
     * Sends an FCM message about user modification
     *
     * @param action will be put as "action" in message data
     * @param modifiedUser will be put as "object" in message data
     *
     * @author Daniil Zavyalov
     */
    fun sendMessage(action: HttpMethod, modifiedUser: UserDTO) {
        val json = Json.encodeToString(modifiedUser)
        val msg: Message = Message.builder()
            .setTopic("user")
            .putData("action", action.toString())
            .putData("object", json)
            .build()
        fcm.send(msg)
    }

    /**
     * Sends an FCM message about booking modification
     *
     * @param action will be put as "action" in message data
     * @param modifiedBooking will be put as "object" in message data
     *
     * @author Daniil Zavyalov
     */
    fun sendMessage(action: HttpMethod, modifiedBooking: BookingDTO) {
        val json = Json.encodeToString(modifiedBooking)
        val msg: Message = Message.builder()
            .setTopic("booking")
            .putData("action", action.toString())
            .putData("object", json)
            .build()
        fcm.send(msg)
    }
}