package band.effective.office.elevator.ui.booking.store

import band.effective.office.elevator.ui.models.WorkSpace
import com.arkivanov.mvikotlin.core.store.Store

interface BookingStore: Store<BookingStore.Intent,BookingStore.State, Nothing> {

    sealed interface Intent {
        object OpenMap: Intent
        object ShowMeetingRooms: Intent
        object ShowWorkplace:Intent
    }

    data class State(
        val listPlace : List<WorkSpace>
    )
}