package band.effective.mattermost.models.response.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Channel(
        @Json(name = "id")
        val id: String,
        @Json(name = "create_at")
        val timeOfCreate: Long,
        @Json(name = "update_at")
        val timeOfLAstUpdate: Long,
        @Json(name = "delete_at")
        val timeOfDelete: Long,
        @Json(name = "team_id")
        val teamId: String,
        @Json(name = "type")
        val channelType: String,
        @Json(name = "display_name")
        val displayName: String,
        @Json(name = "name")
        val name: String,
        @Json(name = "header")
        val header: String,
        @Json(name = "purpose")
        val purpose: String,
        @Json(name = "last_post_at")
        val timeOfLastPost: Long,
        @Json(name = "total_msg_count")
        val totalMsgCount: Int,
        @Json(name = "creator_id")
        val creatorId: String,
        @Json(name = "policy_id")
        val policyId: String?
)