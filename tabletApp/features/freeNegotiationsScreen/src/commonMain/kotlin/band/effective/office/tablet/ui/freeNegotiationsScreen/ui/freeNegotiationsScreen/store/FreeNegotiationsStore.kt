package band.effective.office.tablet.ui.freeNegotiationsScreen.ui.freeNegotiationsScreen.store

import band.effective.office.tablet.domain.model.Booking
import band.effective.office.tablet.domain.model.Organizer
import band.effective.office.tablet.ui.freeNegotiationsScreen.ui.freeNegotiationsScreen.roomUiState.RoomInfoUiState
import com.arkivanov.mvikotlin.core.store.Store
import java.util.Calendar

interface FreeNegotiationsStore :
    Store<FreeNegotiationsStore.Intent, FreeNegotiationsStore.State, Nothing> {

    sealed interface Intent {
        data class OnMainScreen(val reset: Boolean) : Intent
        object CloseModal : Intent
        data class SetBooking(val bookingInfo: Booking) : Intent
        data class OnBookingRoom(val name: RoomInfoUiState, val maxDuration: Int) : Intent
    }

    data class State(
        val isLoad: Boolean,
        val isData: Boolean,
        val error: String?,
        val listRooms: List<RoomInfoUiState>,
        val nameCurrentRoom: String,
        val chosenDurationBooking: Int,
        val realDurationBooking: Int,
        val organizer: Organizer,
        val nameBookingRoom: RoomInfoUiState,
        val showBookingModal: Boolean,
        val currentTime: Calendar,
        val failLoad: Boolean
    ) {
        companion object {
            val defaultState =
                State(
                    isLoad = true,
                    isData = false,
                    error = null,
                    nameCurrentRoom = "",
                    nameBookingRoom = RoomInfoUiState.defaultValue,
                    listRooms = listOf(),
                    chosenDurationBooking = 0,
                    realDurationBooking = 0,
                    organizer = Organizer.default,
                    showBookingModal = false,
                    currentTime = Calendar.getInstance(),
                    failLoad = false
                )
        }
    }
}