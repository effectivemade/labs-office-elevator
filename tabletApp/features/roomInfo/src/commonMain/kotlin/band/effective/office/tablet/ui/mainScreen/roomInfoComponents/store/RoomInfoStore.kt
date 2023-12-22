package band.effective.office.tablet.ui.mainScreen.roomInfoComponents.store

import band.effective.office.tablet.domain.model.EventInfo
import band.effective.office.tablet.domain.model.RoomInfo
import com.arkivanov.mvikotlin.core.store.Store
import java.util.Calendar
import java.util.GregorianCalendar

interface RoomInfoStore : Store<RoomInfoStore.Intent, RoomInfoStore.State, Nothing> {
    sealed interface Intent {
        object OnFreeRoomRequest: Intent
        data class OnChangeSelectDate(val newValue: Calendar): Intent
    }

    data class State(
        val roomInfo: RoomInfo,
        val changeEventTime: Int,
        val selectDate: Calendar,
        val isError: Boolean,
        val nextEvent: EventInfo,
    ) {
        companion object {
            val defaultState =
                State(
                    roomInfo = RoomInfo.defaultValue,
                    changeEventTime = 0,
                    selectDate = GregorianCalendar(),
                    isError = false,
                    nextEvent = EventInfo.emptyEvent
                )
        }
    }
}