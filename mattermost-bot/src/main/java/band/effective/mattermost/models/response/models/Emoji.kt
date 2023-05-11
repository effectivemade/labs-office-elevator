package band.effective.mattermost.models.response.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Emoji(
        @Json(name = "create_at")
        val createAt: Int,
        @Json(name = "creator_id")
        val creatorId: String,
        @Json(name = "delete_at")
        val deleteAt: Long,
        @Json(name = "id")
        val id: String,
        @Json(name = "name")
        val name: String,
        @Json(name = "update_at")
        val updateAt: Int
)