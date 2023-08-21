package office.effective.common.notifications

import io.ktor.http.*
import office.effective.dto.BookingDTO
import office.effective.dto.UserDTO
import office.effective.dto.WorkspaceDTO

interface INotificationSender {

    /**
     * Sends an FCM message about workspace modification
     *
     * @param action will be put as "action" in message data
     * @param modifiedWorkspace will be put as "object" in message data
     *
     * @author Daniil Zavyalov
     */
    fun sendMessage(action: HttpMethod, modifiedWorkspace: WorkspaceDTO)

    /**
     * Sends an FCM message about user modification
     *
     * @param action will be put as "action" in message data
     * @param modifiedUser will be put as "object" in message data
     *
     * @author Daniil Zavyalov
     */
    fun sendMessage(action: HttpMethod, modifiedUser: UserDTO)

    /**
     * Sends an FCM message about booking modification
     *
     * @param action will be put as "action" in message data
     * @param modifiedBooking will be put as "object" in message data
     *
     * @author Daniil Zavyalov
     */
    fun sendMessage(action: HttpMethod, modifiedBooking: BookingDTO)
}