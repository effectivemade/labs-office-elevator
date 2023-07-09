package band.effective.office.tablet.domain.useCase

import band.effective.office.tablet.domain.model.EventInfo
import java.util.Calendar

class CheckBookingUseCase(private val roomInfoUseCase: RoomInfoUseCase) {
    suspend operator fun invoke(event: band.effective.office.tablet.domain.model.EventInfo): Boolean {
        val eventsList: List<band.effective.office.tablet.domain.model.EventInfo> = eventList()
        return !predicate(event, eventsList)
    }

    suspend fun busyEvent(event: band.effective.office.tablet.domain.model.EventInfo): band.effective.office.tablet.domain.model.EventInfo? {
        val eventsList: List<band.effective.office.tablet.domain.model.EventInfo> = eventList()
        return eventsList.firstOrNull() { predicate(event, eventsList) }
    }


    private fun predicate(event: band.effective.office.tablet.domain.model.EventInfo, eventsList: List<band.effective.office.tablet.domain.model.EventInfo>) =
        startEventBelongList(event.startTime, eventsList) || startNewEventBelongList(
            event,
            eventsList
        )

    private suspend fun eventList(): List<band.effective.office.tablet.domain.model.EventInfo> {
        val roomInfo = roomInfoUseCase()
        return if (roomInfo.currentEvent != null) roomInfo.eventList + roomInfo.currentEvent!!
        else roomInfo.eventList
    }

    private fun startNewEventBelongList(event: band.effective.office.tablet.domain.model.EventInfo, eventsList: List<band.effective.office.tablet.domain.model.EventInfo>) =
        eventsList.any { it.startTime.belong(event) }

    private fun startEventBelongList(calendar: Calendar, eventsList: List<band.effective.office.tablet.domain.model.EventInfo>) =
        eventsList.any { calendar.belong(it) }

    private fun Calendar.belong(event: band.effective.office.tablet.domain.model.EventInfo) =
        this > event.startTime && this < event.finishTime
}