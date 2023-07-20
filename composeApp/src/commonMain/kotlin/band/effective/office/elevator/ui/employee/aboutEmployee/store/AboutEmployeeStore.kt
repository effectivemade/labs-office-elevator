package band.effective.office.elevator.ui.employee.aboutEmployee.store

import band.effective.office.elevator.ui.employee.aboutEmployee.store.AboutEmployeeStore.*
import band.effective.office.elevator.ui.models.ReservedSeat
import band.effective.office.elevator.ui.models.User
import com.arkivanov.mvikotlin.core.store.Store

interface AboutEmployeeStore : Store<Intent,State, Nothing>{

    sealed interface Intent {
        object TelephoneClicked : Intent
        object TelegramClicked : Intent
        object BackClicked : Intent
        object TransferMoneyClicked: Intent
    }

    data class State(
        val user: User,
        val reservedSeats: List<ReservedSeat>
    )

}