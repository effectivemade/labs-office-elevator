package band.effective.office.elevator.ui.main.store

import band.effective.office.elevator.ui.employee.aboutEmployee.models.BookingsFilter
import band.effective.office.elevator.ui.models.ElevatorState
import band.effective.office.elevator.ui.models.ReservedSeat
import com.arkivanov.mvikotlin.core.store.Store
import dev.icerock.moko.resources.StringResource
import kotlinx.datetime.LocalDate

interface MainStore : Store<MainStore.Intent, MainStore.State, MainStore.Label> {

    sealed interface Intent {
        object OnClickCallElevator : Intent

        object  OnClickShowOption : Intent
        object  OnClickHideOption : Intent

        object OnClickOpenCalendar : Intent

        object OnClickCloseCalendar : Intent

        data class OnClickApplyDate(val date: LocalDate?) : Intent

        object OpenFiltersBottomDialog : Intent
        data class CloseFiltersBottomDialog(val bookingsFilter: BookingsFilter) : Intent


        data class OnClickExtendBooking(val seat: ReservedSeat) : Intent

        data class OnClickRepeatBooking(val seat: ReservedSeat) : Intent
        data class OnClickDeleteBooking(val seat: ReservedSeat) : Intent
        object OnClickCloseDeleteBooking: Intent
        object OnClickOpenEditBooking: Intent
        object OnClickCloseEditBooking: Intent

    }

    sealed interface Label {
        data class ShowError(val errorState: ErrorState) : Label
        object ShowSuccess : Label

        object ShowOptions : Label
        object  HideOptions : Label

        object CloseCalendar : Label

        object OpenCalendar : Label
        data class OnClickOpenDeleteBooking(val seat: ReservedSeat) : Label
        object OnClickCloseDeleteBooking: Label
        object OnClickOpenEditBooking: Label
        object OnClickCloseEditBooking: Label

        object OpenFiltersBottomDialog: Label
        object CloseFiltersBottomDialog: Label
    }

    data class State(
        val reservedSeats: List<ReservedSeat>,
        val elevatorState: ElevatorState,
        val currentDate: LocalDate,
        val dateFiltrationOnReserves: Boolean
    )

    data class ErrorState(val message: StringResource)
}