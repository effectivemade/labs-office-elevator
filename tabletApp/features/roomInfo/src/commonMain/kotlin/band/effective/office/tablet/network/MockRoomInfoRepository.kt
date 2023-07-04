package band.effective.office.tablet.network

import band.effective.office.tablet.domain.model.EventInfo
import band.effective.office.tablet.domain.model.RoomInfo
import java.util.Calendar
import java.util.GregorianCalendar

class MockRoomInfoRepository : RoomInfoRepository {
    private fun isBusy() = false
    private fun isManyEvent() = false

    private val startCurrentEvent: Calendar
    private val finishCurrentEvent: Calendar
    private val currentTime: Calendar

    init {
        val calendar = GregorianCalendar()
        calendar.add(Calendar.MINUTE, -10)
        startCurrentEvent = calendar.clone() as Calendar
        calendar.add(Calendar.MINUTE, 30)
        finishCurrentEvent = calendar.clone() as Calendar
        currentTime = calendar.clone() as Calendar
    }

    fun getTime(): Calendar{
        currentTime.add(Calendar.MINUTE, 30)
        return currentTime.clone() as Calendar
    }

    private fun currentEvent() = EventInfo(
        startTime = startCurrentEvent,
        finishTime = finishCurrentEvent,
        organizer = "Ольга Белозерова"
    )

    private fun olyaEvent() = EventInfo(
        startTime = getTime(),
        finishTime = getTime(),
        organizer = "Ольга Белозерова"
    )

    private fun matveyEvent() = EventInfo(
        startTime = getTime(),
        finishTime = getTime(),
        organizer = "Матвей Авгуль"
    )

    private fun lilaEvent() = EventInfo(
        startTime = getTime(),
        finishTime = getTime(),
        organizer = "Лилия Акентьева"
    )

    private fun eventsList() = listOf(olyaEvent(), matveyEvent(), lilaEvent())

    private fun bigEventList() =
        eventsList() + eventsList() + eventsList() + eventsList() + eventsList()

    override fun getRoomInfo(name: String): RoomInfo =
        RoomInfo(
            name = "Sirius",
            capacity = 8,
            isHaveTv = true,
            electricSocketCount = 4,
            eventList = if (isManyEvent()) bigEventList() else eventsList(),
            currentEvent = if (isBusy()) currentEvent() else null
        )
}
