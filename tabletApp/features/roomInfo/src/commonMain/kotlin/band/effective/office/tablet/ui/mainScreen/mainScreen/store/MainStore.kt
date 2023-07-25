package band.effective.office.tablet.ui.mainScreen.mainScreen.store

import band.effective.office.tablet.domain.model.RoomInfo
import com.arkivanov.mvikotlin.core.store.Store

interface MainStore : Store<MainStore.Intent, MainStore.State, Nothing> {
    sealed interface Intent {
        object OnBookingCurrentRoomRequest : Intent
        object OnBookingOtherRoomRequest : Intent
        object OnOpenFreeRoomModal : Intent
        object OnFreeRoomIntent : Intent
        object CloseModal : Intent
    }

    data class State(
        val isLoad: Boolean,
        val isData: Boolean,
        val isError: Boolean,
        val roomInfo: RoomInfo,
        val error: String,
        val showBookingModal: Boolean,
        val showFreeModal: Boolean
    ) {
        companion object {
            val defaultState =
                State(
                    isLoad = true,
                    isData = false,
                    isError = false,
                    roomInfo = RoomInfo.defaultValue,
                    error = "",
                    showBookingModal = false,
                    showFreeModal = false
                )
        }
    }
}