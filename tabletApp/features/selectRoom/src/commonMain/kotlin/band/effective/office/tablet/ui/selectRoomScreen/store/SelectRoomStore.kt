package band.effective.office.tablet.ui.selectRoomScreen.store

import com.arkivanov.mvikotlin.core.store.Store

interface SelectRoomStore: Store<SelectRoomStore.Intent, SelectRoomStore.State, Nothing> {

    sealed interface Intent {
        object BookingRoom : Intent
        object CloseModal : Intent
    }

    data class State(
        var isLoad: Boolean,
        var isData: Boolean,
        var isError: Boolean,
        var error: String
    ) {
        companion object {
            val defaultState =
                State(
                    isLoad = false,
                    isData = true,
                    isError = false,
                    error = ""
                )
        }
    }

}