package band.effective.office.elevator.utils

import band.effective.office.elevator.domain.models.EmployeeInfo
import band.effective.office.elevator.ui.employee.allEmployee.EmployeeCard

fun convertEmployeeInfoListToEmployeeCard(employeeInfo: List<EmployeeInfo>):List<EmployeeCard>{
    var employeeCard: List<EmployeeCard> = listOf()
    employeeInfo.forEach { employeeInfo ->
        employeeCard += EmployeeCard(
            employeeInfo.name,
            employeeInfo.post,
            employeeInfo.state,
            employeeInfo.logoUrl
        )
    }
    return employeeCard
}