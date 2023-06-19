package band.effective.mattermost.models.response.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NotifyProps(
    val channel: String,
    val comments: String,
    val desktop: String,
    val desktop_sound: String,
    val desktop_threads: String,
    val email: String,
    val email_threads: String,
    val first_name: String,
    val mention_keys: String,
    val push: String,
    val push_status: String,
    val push_threads: String
)