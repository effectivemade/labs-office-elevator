package band.effective.mattermost.models.error

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MattermostApiError(
        @Json(name = "id")
        val id: String,
        @Json(name = "message")
        val message: String,
        @Json(name = "request_id")
        val requestId: String,
        @Json(name = "status_code")
        val statusCode: Int
)