package band.effective.office.tv.leader.models

data class SpaceStat(
    val participantCount: Int,
    val active_participants: Int,
    val uniqueUsers: Int,
    val participantAverage: Int,
    val regionScope: Int,
    val monthEventCount: Int,
    val ntiPercent: Double,
    val moderationTime: String
)
