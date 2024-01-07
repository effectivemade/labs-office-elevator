package band.effective.office.tablet.domain.model

import java.util.Calendar

sealed class Slot {
    abstract val start: Calendar
    abstract val finish: Calendar

    data class EmptySlot(
        override val start: Calendar,
        override val finish: Calendar,
    ) : Slot()

    data class EventSlot(
        override val start: Calendar,
        override val finish: Calendar,
        val eventInfo: EventInfo
    ) : Slot()
}