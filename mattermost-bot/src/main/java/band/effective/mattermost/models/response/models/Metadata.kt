package band.effective.mattermost.models.response.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Metadata(
        val acknowledgements: List<Acknowledgement>?,
        val embeds: List<Embed>?,
        val emojis: List<Emoji>?,
        val files: List<File>?,
        val images: Images?,
        val priority: Priority?,
        val reactions: List<Reaction>?
)