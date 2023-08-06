package band.effective.office.elevator.ui.booking.store

import band.effective.office.elevator.ui.models.Place
import com.arkivanov.mvikotlin.core.store.Store
import kotlinx.datetime.LocalDate

interface BookingStore: Store<BookingStore.Intent,BookingStore.State, BookingStore.Label> {

    sealed interface Intent {
        object OpenChooseZone: Intent
        object CloseChooseZone: Intent
        object CloseRepeatDialog : Intent
        object OpenRepeatDialog : Intent
        object OpenBookAccept: Intent
        object CloseBookAccept: Intent
        object OpenBookPeriod: Intent

        object CloseBookPeriod: Intent
        object OpenCalendar : Intent

        object CloseCalendar : Intent

        data class ApplyDate(val date: LocalDate?) : Intent
        data class ShowPlace(val type:String): Intent
    }

    data class State(
        val listPlace : List<Place>,
        val currentDate: LocalDate
    )

    sealed interface Label{
        object OpenChooseZone : Label
        object CloseChooseZone : Label
        object OpenBookPeriod: Label
        object CloseBookPeriod: Label
        object CloseRepeatDialog : Label
        object OpenRepeatDialog : Label
        object OpenBookAccept: Label
        object CloseBookAccept: Label
        object CloseCalendar : Label

        object OpenCalendar : Label
    }
}