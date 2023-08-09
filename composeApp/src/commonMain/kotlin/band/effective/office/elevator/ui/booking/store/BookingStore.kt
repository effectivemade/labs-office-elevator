package band.effective.office.elevator.ui.booking.store

import band.effective.office.elevator.ui.booking.models.WorkSpaceUI
import com.arkivanov.mvikotlin.core.store.Store
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

interface BookingStore: Store<BookingStore.Intent,BookingStore.State, BookingStore.Label> {

    sealed interface Intent {
        object OpenChooseZone: Intent
        object OpenTimeModal: Intent
        object CloseTimeModal: Intent
        data class ApplyTime(val time: LocalTime) : Intent
        object CloseChooseZone: Intent
        object OpenRepeatDialog : Intent
        object CloseBookRepeat : Intent
        object OpenBookRepeat : Intent
        object OpenBookAccept: Intent
        object CloseBookAccept: Intent
        object OpenBookPeriod: Intent
        object OpenConfirmBooking: Intent
        object SearchSuitableOptions: Intent

        object CloseBookPeriod: Intent
        object OpenCalendar : Intent

        object CloseCalendar : Intent
        object OpenMainScreen: Intent
        object CloseConfirmBooking: Intent

        data class ApplyDate(val date: LocalDate?) : Intent
        data class ShowPlace(val type:String): Intent
    }

    data class State(
        val workSpaces : List<WorkSpaceUI>,
        val currentDate: LocalDate
    )

    sealed interface Label{
        object OpenChooseZone : Label
        object OpenConfirmBooking: Label
        object CloseChooseZone : Label
        object OpenBookPeriod: Label
        object CloseBookPeriod: Label
        object CloseRepeatDialog : Label
        object OpenRepeatDialog : Label
        object OpenBookAccept: Label
        object CloseBookAccept: Label
        object CloseCalendar : Label

        object OpenCalendar : Label
        object CloseConfirmBooking: Label
        object OpenTimeModal: Label
        object CloseTimeModal: Label
        object CloseBookRepeat : Label
        object OpenBookRepeat : Label
    }
}