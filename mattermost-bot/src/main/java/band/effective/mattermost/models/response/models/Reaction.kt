package band.effective.mattermost.models.response.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Reaction(
    val create_at: Long,
    val emoji_name: String,
    val post_id: String,
    val user_id: String
)