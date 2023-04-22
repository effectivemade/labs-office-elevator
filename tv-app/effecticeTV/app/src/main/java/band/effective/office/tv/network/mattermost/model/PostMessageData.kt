package band.effective.office.tv.network.mattermost.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PostMessageData(
    @Json(name = "channel_id")
    val channelId: String,
    val message: String,
    @Json(name = "root_id")
    val rootId: String
)
