package band.effective.office.tv.network.leader.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SocialNetworkX(
    val alias: String,
    val url: String
)