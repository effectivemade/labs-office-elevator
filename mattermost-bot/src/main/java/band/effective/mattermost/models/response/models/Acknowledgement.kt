package band.effective.mattermost.models.response.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Acknowledgement(
    val acknowledged_at: Int,
    val post_id: String,
    val user_id: String
)