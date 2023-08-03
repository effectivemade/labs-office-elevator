package band.effective.office.elevator.ui.booking.store

import band.effective.office.elevator.ui.models.Place
import com.arkivanov.mvikotlin.core.store.Store

interface BookingStore: Store<BookingStore.Intent,BookingStore.State, Nothing> {

    sealed interface Intent {
        object OpenChooseZone: Intent
        data class ShowPlace(val type:String): Intent
    }

    data class State(
        val listPlace : List<Place>
    )
}