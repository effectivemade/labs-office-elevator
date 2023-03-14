package band.effective.office.tv.network.leader.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class StatX(
    @Json(name = "active_participants")
    val activeParticipants: Int = 0,
    val moderationTime: String = "",
    val monthEventCount: Int = 0,
    val ntiPercent: Double = 0.0,
    val participantAverage: String = "",
    val participantCount: Int = 0,
    val regionScope: Int = 0,
    val uniqueUsers: Int = 0
)

