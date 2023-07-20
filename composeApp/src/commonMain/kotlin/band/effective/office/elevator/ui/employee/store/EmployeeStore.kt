package band.effective.office.elevator.ui.employee.store

import band.effective.office.elevator.ui.employee.EmployeeCard
import com.arkivanov.mvikotlin.core.store.Store

interface EmployeeStore: Store<EmployeeStore.Intent, EmployeeStore.State, EmployeeStore.Label> {
    sealed interface Intent{
        object OnTextFieldUpdate:Intent
        object OnClickOnEmployee:Intent
    }

    data class State(
        val changeShowedEmployeeCards: List<EmployeeCard>,
    )

    sealed interface Label{
        object ShowProfileScreen: Label
    }

}
