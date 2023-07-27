package band.effective.office.tablet.ui.freeNegotiationsScreen.ui.freeNegotiationsScreen.store

import band.effective.office.tablet.domain.model.EventInfo
import band.effective.office.tablet.domain.model.RoomInfo
import com.arkivanov.mvikotlin.core.store.Store

interface FreeNegotiationsStore : Store< FreeNegotiationsStore.Intent, FreeNegotiationsStore.State, Nothing>{

    sealed interface Intent{
        object OnBookingRoom : Intent
        object OnMainScreen : Intent
        object CloseModal : Intent
        data class SetEvent(val eventInfo: EventInfo): Intent
    }

    data class State(
        val isLoad: Boolean,
        val isData: Boolean,
        val error: String?,
        val listFreeRooms: List<RoomInfo>,
        val eventInfo: EventInfo,
        val showBookingModal: Boolean
    ) {
        companion object {
            val defaultState =
                State(
                    isLoad = true,
                    isData = false,
                    error = null,
                    listFreeRooms = listOf(),
                    eventInfo = EventInfo.emptyEvent,
                    showBookingModal = false
                )
        }
    }
}