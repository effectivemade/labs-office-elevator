package band.effective.office.tablet.domain.useCase

import band.effective.office.tablet.domain.model.EventInfo
import java.util.Calendar

class CheckBookingUseCase(private val roomInfoUseCase: RoomInfoUseCase) {
    suspend operator fun invoke(event: EventInfo): Boolean {
        val eventsList: List<EventInfo> = eventList()
        return !predicate(event, eventsList)
    }

    suspend fun busyEvent(event: EventInfo): EventInfo? {
        val eventsList: List<EventInfo> = eventList()
        return eventsList.firstOrNull() { it.startTime.belong(event) || event.startTime.belong(it) }?.copy()
    }


    private fun predicate(event: EventInfo, eventsList: List<EventInfo>) =
        startEventBelongList(event.startTime, eventsList) || startNewEventBelongList(
            event,
            eventsList
        )

    private suspend fun eventList(): List<EventInfo> {
        val roomInfo = roomInfoUseCase()
        return if (roomInfo.currentEvent != null) roomInfo.eventList + roomInfo.currentEvent!!
        else roomInfo.eventList
    }

    private fun startNewEventBelongList(event: EventInfo, eventsList: List<EventInfo>) =
        eventsList.any { it.startTime.belong(event) }

    private fun startEventBelongList(calendar: Calendar, eventsList: List<EventInfo>) =
        eventsList.any { calendar.belong(it) }

    private fun Calendar.belong(event: EventInfo) =
        this >= event.startTime && this <= event.finishTime
}