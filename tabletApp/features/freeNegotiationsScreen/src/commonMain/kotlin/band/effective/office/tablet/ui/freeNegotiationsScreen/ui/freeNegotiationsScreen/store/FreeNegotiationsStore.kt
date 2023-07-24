package band.effective.office.tablet.ui.freeNegotiationsScreen.ui.freeNegotiationsScreen.store

import band.effective.office.tablet.domain.model.RoomInfo
import com.arkivanov.mvikotlin.core.store.Store

interface FreeNegotiationsStore : Store< FreeNegotiationsStore.Intent, FreeNegotiationsStore.State, Nothing>{

    sealed interface Intent{
        object OnBookingRoom : Intent
        object OnMainScreen : Intent
        object CloseModal : Intent
    }

    data class State(
        val isLoad: Boolean,
        val isData: Boolean,
        val isError: Boolean,
        val listFreeRooms: List<RoomInfo>,
        val error: String,
        val showBookingModal: Boolean
    ) {
        companion object {
            val defaultState =
                State(
                    isLoad = true,
                    isData = false,
                    isError = false,
                    listFreeRooms = listOf(),
                    error = "",
                    showBookingModal = false
                )
        }
    }
}