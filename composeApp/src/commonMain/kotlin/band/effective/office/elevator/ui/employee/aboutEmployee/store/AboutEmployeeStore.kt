package band.effective.office.elevator.ui.employee.aboutEmployee.store

import band.effective.office.elevator.domain.models.User
import band.effective.office.elevator.ui.models.ReservedSeat
import com.arkivanov.mvikotlin.core.store.Store
import kotlinx.datetime.LocalDate

interface AboutEmployeeStore : Store<AboutEmployeeStore.Intent,AboutEmployeeStore.State,AboutEmployeeStore.Label>{

    sealed interface Intent {
        object TelephoneClicked : Intent
        object TelegramClicked : Intent
        object BackClicked : Intent
        object TransferMoneyClicked: Intent
        object OpenCalendarClicked: Intent
        object CloseCalendarClicked: Intent
        data class OnClickApplyDate(val date: LocalDate?): Intent
        object OpenBottomDialog: Intent
        object CloseBottomDialog: Intent

    }

    data class State(
        val user: User,
        val reservedSeats: List<ReservedSeat>,
        val filtrationOnReserves: Boolean,
        val currentDate: LocalDate
    )

    sealed interface Label{
        object OpenCalendar: Label
        object CloseCalendar: Label
        object OpenBottomDialog: Label
        object CloseBottomDialog: Label
    }

}