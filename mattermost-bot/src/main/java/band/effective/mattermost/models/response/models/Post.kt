package band.effective.mattermost.models.response.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Post(
        @Json(name = "id")
        val id: String?,
        val create_at: Long?,
        @Json(name = "channel_id")
        val channel_id: String?,
        val delete_at: Long?,
        val edit_at: Int?,
        val file_ids: List<String>?,
        val hashtag: String?,
        val message: String?,
        val metadata: Metadata,
        val original_id: String?,
        val pending_post_id: String?,
        val props: Props?,
        val root_id: String?,
        val type: String?,
        val update_at: Long?,
        val user_id: String?
)