package band.effective.office.elevator.utils

import band.effective.office.elevator.ui.employee.allEmployee.EmployeeCard
import band.effective.office.elevator.ui.employee.allEmployee.EmployeesData

fun changeEmployeeShowedList(query: String): List<EmployeeCard> {
    return if(query.isEmpty()){
        EmployeesData.employeesCardData
    }else {
        val compareString = query.filter { !it.isWhitespace() }.lowercase()
        val allEmployeesCards = EmployeesData.employeesCardData

        val showedEmployeesCards  = allEmployeesCards.filter { it.name.filter { !it.isWhitespace() }
            .lowercase().contains(compareString) }


        showedEmployeesCards
    }
}