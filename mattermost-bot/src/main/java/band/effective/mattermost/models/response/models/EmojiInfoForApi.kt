package band.effective.mattermost.models.response.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EmojiInfoForApi(
    @Json(name = "create_at")
    val createAt: Int,
    @Json(name = "emoji_name")
    val emojiName: String,
    @Json(name = "post_id")
    val postId: String,
    @Json(name = "user_id")
    val userId: String?
) {
    fun mapForApi()  = mapOf(
            "create_at" to createAt,
            "emoji_name" to emojiName,
            "post_id" to postId,
            "user_id" to userId
    )
}

@JsonClass(generateAdapter = true)
data class EmojiInfo(
        @Json(name = "create_at")
        val createAt: Long,
        @Json(name = "emoji_name")
        val emojiName: String,
        @Json(name = "post_id")
        val postId: String,
) {
    fun mapForApi(userId: String)  = mapOf(
            "create_at" to createAt,
            "emoji_name" to emojiName,
            "post_id" to postId,
            "user_id" to userId
    )
}