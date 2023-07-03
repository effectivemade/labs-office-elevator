package band.effective.office.tablet.network

import band.effective.office.tablet.domain.model.EventInfo
import band.effective.office.tablet.domain.model.RoomInfo
import java.util.GregorianCalendar

class MockRoomInfoRepository: RoomInfoRepository {

    private val currentEvent = EventInfo(
        startTime = GregorianCalendar(),
        finishTime = GregorianCalendar(),
        organizer = "Ольга Белозерова"
    )

    private val olyaEvent = EventInfo(
        startTime = GregorianCalendar(),
        finishTime = GregorianCalendar(),
        organizer = "Ольга Белозерова"
    )

    private val matveyEvent = EventInfo(
        startTime = GregorianCalendar(),
        finishTime = GregorianCalendar(),
        organizer = "Матвей Авгуль"
    )

    private val lilaEvent = EventInfo(
        startTime = GregorianCalendar(),
        finishTime = GregorianCalendar(),
        organizer = "Лилия Акентьева"
    )

    override fun getRoomInfo(name: String): RoomInfo =
        RoomInfo(
            name = "Sirius",
            capacity = 8,
            isHaveTv = true,
            electricSocketCount = 4,
            eventList = listOf(olyaEvent,matveyEvent,lilaEvent),
            currentEvent = currentEvent
        )
}
