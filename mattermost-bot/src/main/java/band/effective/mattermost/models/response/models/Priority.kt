package band.effective.mattermost.models.response.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Priority(
    val priority: String?,
    val requested_ack: Boolean?
)