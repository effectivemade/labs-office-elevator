package band.effective.office.tablet.ui.mainScreen.mainScreen.store

import band.effective.office.tablet.domain.model.EventInfo
import band.effective.office.tablet.domain.model.RoomInfo
import com.arkivanov.mvikotlin.core.store.Store

interface MainStore : Store<MainStore.Intent, MainStore.State, Nothing> {
    sealed interface Intent {
        object OnOpenFreeRoomModal : Intent
        object OnBookingOtherRoomRequest : Intent
        data class OnDisconnectChange(val newValue: Boolean) : Intent
        object RebootRequest : Intent
        data class OnChangeEventRequest(val eventInfo: EventInfo) : Intent
        data class OnSelectRoom(val index: Int) : Intent
        object OnUpdate : Intent
    }

    data class State(
        val isLoad: Boolean,
        val isData: Boolean,
        val isError: Boolean,
        val isDisconnect: Boolean,
        val updatedEvent: EventInfo,
        val isSettings: Boolean,
        val roomList: List<RoomInfo>,
        val indexSelectRoom: Int,
    ) {
        companion object {
            val defaultState =
                State(
                    isLoad = true,
                    isData = false,
                    isError = false,
                    isDisconnect = false,
                    isSettings = false,
                    updatedEvent = EventInfo.emptyEvent,
                    roomList = listOf(),
                    indexSelectRoom = 0
                )
        }
    }
}