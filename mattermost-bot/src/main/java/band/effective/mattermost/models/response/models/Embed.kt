package band.effective.mattermost.models.response.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Embed(
        @Json(name = "data")
        val dataOFEmbed: Data?,
        @Json(name = "type")
        val type: String,
        @Json(name = "url")
        val url: String
)