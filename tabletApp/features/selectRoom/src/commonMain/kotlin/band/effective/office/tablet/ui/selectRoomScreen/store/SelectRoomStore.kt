package band.effective.office.tablet.ui.selectRoomScreen.store

import band.effective.office.tablet.domain.model.Booking
import com.arkivanov.mvikotlin.core.store.Store

interface SelectRoomStore: Store<SelectRoomStore.Intent, SelectRoomStore.State, Nothing> {

    sealed interface Intent {
        object BookingRoom : Intent
        object CloseModal : Intent
        data class SetBooking(val booking: Booking): Intent
    }

    data class State(
        var isData: Boolean,
        var isSuccess: Boolean,
        var isError: Boolean,
        var error: String,
        val booking: Booking
    ) {
        companion object {
            val defaultState =
                State(
                    isData = true,
                    isSuccess = false,
                    isError = false,
                    error = "",
                    booking = Booking.default
                )
        }
    }

}