package band.effective.office.elevator.ui.employee.allEmployee.models.mappers

import band.effective.office.elevator.domain.models.EmployeeInfo

fun EmployeeInfo.toUI(): EmployeeCard {

    return EmployeeCard(name = name, post = post, state = state, logoUrl = logoUrl)
}