package band.effective.office.tablet.network.api

import band.effective.office.tablet.domain.model.EventInfo
import band.effective.office.tablet.domain.model.RoomInfo
import band.effective.office.tablet.network.model.WebServerEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.GregorianCalendar

class WorkApi : Api {
    val mutableRoomInfo = MutableStateFlow(RoomInfo.defaultValue)
    val mutableOrgList =
        MutableStateFlow(listOf("Ольга Белозерова", "Матвей Авгуль", "Лилия Акентьева"))

    override suspend fun getRoomInfo(): RoomInfo {
        return mutableRoomInfo.value
    }

    override suspend fun getOrganizers(): List<String> {
        return mutableOrgList.value
    }

    override suspend fun cancelEvent(): Boolean {
        return false
    }

    override suspend fun bookingRoom(): Boolean {
        return false
    }

    override fun subscribeOnWebHock(
        scope: CoroutineScope,
        handler: (event: WebServerEvent) -> Unit
    ) {
        scope.launch {
            mutableRoomInfo.collect { handler(WebServerEvent.RoomInfoUpdate) }
        }
        scope.launch {
            mutableOrgList.collect {
                handler(WebServerEvent.OrganizerInfoUpdate)
            }
        }
    }

    private val startCurrentEvent: Calendar
    private val finishCurrentEvent: Calendar

    /**It's field contain time for mocks, when it is used it needs to be increment, so that different mocks have different times*/
    private val currentTime: Calendar

    init {
        val calendar = GregorianCalendar()
        calendar.add(Calendar.MINUTE, -10)
        startCurrentEvent = calendar.clone() as Calendar
        calendar.add(Calendar.MINUTE, 30)
        finishCurrentEvent = calendar.clone() as Calendar
        currentTime = calendar.clone() as Calendar

        mutableRoomInfo.update {
            RoomInfo(
                name = "Sirius",
                capacity = 4,
                isHaveTv = false,
                electricSocketCount = 2,
                eventList = eventsList(),
                currentEvent = currentEvent()
            )
        }
    }

    private fun getTime(): Calendar {
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

    fun changeBusy(newValue: Boolean) {
        if (newValue) {
            mutableRoomInfo.update { it.copy(currentEvent = null) }
        } else {
            mutableRoomInfo.update { it.copy(currentEvent = currentEvent()) }
        }
    }

    fun changeEventCount(isMany: Boolean) {
        updateCurrentTime()
        if (isMany) {
            mutableRoomInfo.update { it.copy(eventList = bigEventList()) }
        } else {
            mutableRoomInfo.update { it.copy(eventList = eventsList()) }
        }
    }

    private fun updateCurrentTime() {
        val calendar = GregorianCalendar()
        calendar.add(Calendar.MINUTE, 20)
        currentTime.time = calendar.time
    }
}