package band.effective.office.elevator.ui.employee.store

import band.effective.office.elevator.ui.employee.employeeCard
import com.arkivanov.mvikotlin.core.store.Store

interface EmployeeStore: Store<EmployeeStore.Intent,EmployeeStore.State,EmployeeStore.Label>{
    sealed interface Intent{
        object OnUserSearchClick:Intent
        object OnClickOnEmployee:Intent
    }

    data class State(
        val changeShowedEmployeeCards: List<employeeCard>,
    )

    sealed interface Label{
        object ShowProfileScreen: Label
    }

}