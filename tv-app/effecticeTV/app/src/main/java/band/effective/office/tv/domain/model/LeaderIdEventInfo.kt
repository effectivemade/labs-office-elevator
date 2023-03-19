package band.effective.office.tv.domain.model

import java.util.GregorianCalendar

data class LeaderIdEventInfo(
    val id: Int,
    val name: String,
    val dateTime: GregorianCalendar,
    val isOnline: Boolean,
    val photoUrl: String,
    val organizer: String? = null,
    val speakers: List<String>? = null
){
    constructor(errorMessege: String): this(
        id = -1,
        name = errorMessege,
        dateTime = GregorianCalendar(),
        isOnline = false,
        photoUrl = ""
    )
}