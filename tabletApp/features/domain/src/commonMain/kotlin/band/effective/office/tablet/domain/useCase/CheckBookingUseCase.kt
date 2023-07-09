package band.effective.office.tablet.domain.useCase

import band.effective.office.tablet.domain.model.EventInfo
import java.util.Calendar

class CheckBookingUseCase(private val roomInfoUseCase: RoomInfoUseCase) {
    suspend operator fun invoke(event: EventInfo): Boolean {
        val roomInfo = roomInfoUseCase()
        val eventsList: List<EventInfo> =
            if (roomInfo.currentEvent == null) roomInfo.eventList
            else roomInfo.eventList + roomInfo.currentEvent
        val predicate = startEventBelongList(event.startTime, eventsList) || startNewEventBelongList( event, eventsList )
        return !predicate
    }

    private fun startNewEventBelongList(event: EventInfo, eventsList: List<EventInfo>) =
        eventsList.any { it.startTime.belong(event) }

    private fun startEventBelongList(calendar: Calendar, eventsList: List<EventInfo>) =
        eventsList.any { calendar.belong(it) }

    private fun Calendar.belong(event: EventInfo) =
        this > event.startTime && this < event.finishTime
}