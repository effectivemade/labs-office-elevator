package band.effective.mattermost.models.response

import band.effective.mattermost.models.response.models.Channel
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResponseGetChannels(
        val channels: List<Channel>
)