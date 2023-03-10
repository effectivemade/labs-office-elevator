package band.effective.office.tv.network.leader.models

data class StatX(
    val active_participants: Int,
    val moderationTime: String,
    val monthEventCount: Int,
    val ntiPercent: Int,
    val participantAverage: String,
    val participantCount: Int,
    val regionScope: Int,
    val uniqueUsers: Int
)