package band.effective.office.elevator.utils

import band.effective.office.elevator.ui.employee.allEmployee.EmployeeCard

fun changeEmployeeShowedList(query: String, allEmployeesCards: List<EmployeeCard>): List<EmployeeCard> {
    return if(query.isEmpty()){
        //EmployeesData.employeesCardData
        allEmployeesCards
    }else {
        val compareString = query.filter { !it.isWhitespace() }.lowercase()
        //val allEmployeesCards = EmployeesData.employeesCardData

        val showedEmployeesCards  = allEmployeesCards.filter { it.name.filter { !it.isWhitespace() }
            .lowercase().contains(compareString) }


        showedEmployeesCards
    }
}