package band.effective.office.elevator.ui.employee.allEmployee.store

import band.effective.office.elevator.domain.models.EmployeeInfo
import band.effective.office.elevator.ui.employee.allEmployee.models.mappers.EmployeeCard
import com.arkivanov.mvikotlin.core.store.Store

interface EmployeeStore: Store<EmployeeStore.Intent, EmployeeStore.State, EmployeeStore.Label> {
    sealed interface Intent{
        data class OnTextFieldUpdate(val query: String): Intent
        data class OnClickOnEmployee(val employeeId:String): Intent
    }

    data class State(
        val changeShowedEmployeeCards: List<EmployeeCard>,
        val countShowedEmployeeCards:String,
        val countInOfficeShowedEmployeeCards:String,
        val query:String
    )

    sealed interface Label{
        data class ShowProfileScreen(val employee:EmployeeInfo): Label
    }

}
