package band.effective.office.tablet.ui.freeNegotiationsScreen.ui.freeNegotiationsScreen.store

import band.effective.office.tablet.domain.model.Booking
import band.effective.office.tablet.domain.model.EventInfo
import band.effective.office.tablet.domain.model.RoomInfo
import com.arkivanov.mvikotlin.core.store.Store

interface FreeNegotiationsStore : Store< FreeNegotiationsStore.Intent, FreeNegotiationsStore.State, Nothing>{

    sealed interface Intent{
        object OnBookingRoom : Intent
        object OnMainScreen : Intent
        object CloseModal : Intent
        data class SetBooking(val bookingInfo: Booking): Intent
    }

    data class State(
        val isLoad: Boolean,
        val isData: Boolean,
        val error: String?,
        val listRooms: List<RoomInfo>,
        val nameRoom: String,
        val eventInfo: EventInfo,
        val showBookingModal: Boolean
    ) {
        companion object {
            val defaultState =
                State(
                    isLoad = false,
                    isData = true,
                    error = null,
                    nameRoom = "",
                    listRooms = listOf(),
                    eventInfo = EventInfo.emptyEvent,
                    showBookingModal = false
                )
        }
    }
}