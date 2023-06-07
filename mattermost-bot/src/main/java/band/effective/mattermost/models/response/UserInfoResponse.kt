package band.effective.mattermost.models.response

import band.effective.mattermost.models.UserInfo
import band.effective.mattermost.models.response.models.NotifyProps
import band.effective.mattermost.models.response.models.Timezone
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserInfoResponse(
        val auth_data: String,
        val auth_service: String,
        val bot_description: String,
        val create_at: Long,
        val delete_at: Long,
        val disable_welcome_email: Boolean,
        val email: String,
        val first_name: String,
        val id: String,
        val is_bot: Boolean,
        val last_name: String,
        val last_password_update: Long,
        val last_picture_update: Long,
        val locale: String,
        val nickname: String,
        val notify_props: NotifyProps,
        val position: String,
        val roles: String,
        val timezone: Timezone,
        val update_at: Long,
        val username: String
)

fun UserInfoResponse.toUserInfo() = UserInfo(id)