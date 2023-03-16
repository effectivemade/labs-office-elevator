package band.effective.office.tv.model

import java.util.GregorianCalendar

data class LeaderIdEventInfo(
    val id: Int,
    val name: String,
    val dateTime: GregorianCalendar,
    val isOnline: Boolean,
    val photoUrl: String,
    val organizer: String? = null,
    val speakers: List<String>? = null
)