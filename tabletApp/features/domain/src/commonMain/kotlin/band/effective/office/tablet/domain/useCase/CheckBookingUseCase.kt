package band.effective.office.tablet.domain.useCase

import band.effective.office.tablet.domain.model.EventInfo
import java.util.Calendar

/**Use case for checking booking room opportunity*/
class CheckBookingUseCase(private val roomInfoUseCase: RoomInfoUseCase) {
    /**
     * @return Event busy with room booking, if room free, return null*/
    suspend operator fun invoke(event: EventInfo) =
        eventList().firstOrNull() { it.startTime.belong(event) || event.startTime.belong(it) }
            ?.copy()

    /**
     * @return All events in current room*/
    private suspend fun eventList(): List<EventInfo> {
        val roomInfo = roomInfoUseCase()
        return if (roomInfo.currentEvent != null) roomInfo.eventList + roomInfo.currentEvent!!
        else roomInfo.eventList
    }

    /**
     * @return True, if the moment belongs to the time interval between event start and end*/
    private fun Calendar.belong(event: EventInfo) =
        this >= event.startTime && this <= event.finishTime
}