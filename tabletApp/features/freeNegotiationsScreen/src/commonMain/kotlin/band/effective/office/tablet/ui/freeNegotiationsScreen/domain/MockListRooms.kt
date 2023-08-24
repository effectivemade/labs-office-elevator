package band.effective.office.tablet.ui.freeNegotiationsScreen.domain

import band.effective.office.tablet.domain.model.EventInfo
import band.effective.office.tablet.domain.model.Organizer
import band.effective.office.tablet.domain.model.RoomInfo
import java.util.Calendar

object MockListRooms {
    val listRooms = listOf(
        RoomInfo(
            name = "Pluto",
            capacity = 3,
            isHaveTv = false,
            socketCount = 0,
            eventList = listOf(
                EventInfo(
                    startTime = addTime(0, 0, 30),
                    finishTime = addTime(0, 1, 0),
                    organizer = "Ольга Белозёрова".toOrg(),
                    id = ""
                )
            ),
            currentEvent = null,
            id = ""
        ),

        RoomInfo(
            name = "Moon",
            capacity = 8,
            isHaveTv = false,
            socketCount = 0,
            eventList = listOf(
                EventInfo(
                    startTime = addTime(1, 2, 30),
                    finishTime = addTime(1, 3, 30),
                    organizer = "Ольга Белозёрова".toOrg(),
                    id = ""
                )
            ),
            currentEvent = null,
            id = ""
        ),

        RoomInfo(
            name = "Antares",
            capacity = 3,
            isHaveTv = false,
            socketCount = 14,
            eventList = listOf(
                EventInfo(
                    startTime = addTime(0, 0 , 0),
                    finishTime = addTime(0, 1, 15),
                    organizer = "Ольга Белозёрова".toOrg(),
                    id = ""
                )
            ),
            currentEvent = EventInfo(
                startTime = addTime(0, 0 , 0),
                finishTime = addTime(0, 1, 15),
                organizer = "Ольга Белозёрова".toOrg(),
                id = ""
            ),
            id = ""
        ),

        RoomInfo(
            name = "Sun",
            capacity = 8,
            isHaveTv = false,
            socketCount = 0,
            eventList = listOf(
                EventInfo(
                    startTime = addTime(0, 0 , 0),
                    finishTime = addTime(0, 0, 45),
                    organizer = "Коровянский А.".toOrg(),
                    id = ""
                )
            ),
            currentEvent = EventInfo(
                startTime = addTime(0, 0 , 0),
                finishTime = addTime(0, 0, 45),
                organizer = "Коровянский А.".toOrg(),
                id = ""
            ),
            id = ""
        )
    )

    private fun addTime(d: Int, h: Int, min: Int): Calendar {
        val currentTime = Calendar.getInstance()
        currentTime.add(Calendar.DAY_OF_MONTH, d)
        currentTime.add(Calendar.HOUR, h)
        currentTime.add(Calendar.MINUTE, min)
        return currentTime
    }
}

private fun String.toOrg(): Organizer = Organizer(fullName = this, id = this, email = this)
