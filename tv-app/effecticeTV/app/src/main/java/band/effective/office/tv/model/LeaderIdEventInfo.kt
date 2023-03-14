package band.effective.office.tv.model

import java.util.GregorianCalendar

data class LeaderIdEventInfo(
    val name: String,
    val dateTime: GregorianCalendar,
    val organizer: String,
    val speakers: List<String>,
    val urlToLeaderIdSite: String
)