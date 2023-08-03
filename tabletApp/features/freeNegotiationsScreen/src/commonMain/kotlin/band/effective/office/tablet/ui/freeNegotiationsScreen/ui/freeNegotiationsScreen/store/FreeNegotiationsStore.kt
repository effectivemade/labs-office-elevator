package band.effective.office.tablet.ui.freeNegotiationsScreen.ui.freeNegotiationsScreen.store

import band.effective.office.tablet.domain.model.Booking
import band.effective.office.tablet.domain.model.EventInfo
import band.effective.office.tablet.domain.model.RoomInfo
import band.effective.office.tablet.ui.freeNegotiationsScreen.ui.freeNegotiationsScreen.roomUiState.RoomInfoUiState
import com.arkivanov.mvikotlin.core.store.Store
import java.util.Calendar

interface FreeNegotiationsStore : Store< FreeNegotiationsStore.Intent, FreeNegotiationsStore.State, Nothing>{

    sealed interface Intent{
        data class OnMainScreen(val reset: Boolean) : Intent
        object CloseModal : Intent
        data class SetBooking(val bookingInfo: Booking): Intent
        data class OnBookingRoom(val name: String, val maxDuration: Int) : Intent
    }

    data class State(
        val isLoad: Boolean,
        val isData: Boolean,
        val error: String?,
        val listRooms: List<RoomInfoUiState>,
        val nameCurrentRoom: String,
        val chosenDurationBooking: Int,
        val realDurationBooking: Int,
        val organizer: String,
        val nameBookingRoom: String,
        val showBookingModal: Boolean,
        val currentTime: Calendar
    ) {
        companion object {
            val defaultState =
                State(
                    isLoad = false,
                    isData = true,
                    error = null,
                    nameCurrentRoom = "",
                    nameBookingRoom = "",
                    listRooms = listOf(),
                    chosenDurationBooking = 0,
                    realDurationBooking = 0,
                    organizer = "",
                    showBookingModal = false,
                    currentTime = Calendar.getInstance()
                )
        }
    }
}