package band.effective.office.tablet.domain.model

import java.util.Calendar
import java.util.GregorianCalendar

data class EventInfo(
    val startTime: Calendar,
    val finishTime: Calendar,
    val organizer: Organizer,
    val id: String
){
    companion object{
        val emptyEvent = EventInfo(
            startTime = GregorianCalendar(),
            finishTime = GregorianCalendar(),
            organizer = Organizer.default,
            id = ""
        )
    }
}
