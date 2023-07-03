package band.effective.office.tablet.domain.model

import java.util.Calendar

data class EventInfo(
    val startTime: Calendar,
    val finishTime: Calendar,
    val organizer: String
)
