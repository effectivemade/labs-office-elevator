package band.effective.office.tv.network.mattermost.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Post(
    @Json(name = "channel_id")
    val channelId: String,
    @Json(name = "create_at")
    val createAt: Long,
    @Json(name = "delete_at")
    val deleteAt: Int,
    @Json(name = "edit_at")
    val editAt: Int,
    val hashtags: String,
    val id: String,
    @Json(name = "is_pinned")
    val isPinned: Boolean,
    @Json(name = "last_reply_at")
    val lastReplyAt: Int,
    val message: String,
    val metadata: Metadata,
    @Json(name = "original_id")
    val originalId: String,
    val participants: Any?,
    @Json(name = "pending_post_id")
    val pendingPostId: String,
    val props: Props,
    @Json(name = "reply_count")
    val replyCount: Int,
    @Json(name = "root_id")
    val rootId: String,
    val type: String,
    @Json(name = "update_at")
    val updateAt: Long,
    @Json(name = "user_id")
    val userId: String
)