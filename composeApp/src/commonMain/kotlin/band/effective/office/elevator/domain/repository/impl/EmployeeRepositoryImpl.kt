package band.effective.office.elevator.domain.repository.impl

import band.effective.office.elevator.domain.models.EmployeeInfo
import band.effective.office.elevator.domain.repository.EmployeeRepository
import kotlinx.coroutines.flow.MutableStateFlow

class EmployeeRepositoryImpl: EmployeeRepository{//private val api:EmployeeRepository: EmployeeRepository
    private val employList = MutableStateFlow(listOf(
        EmployeeInfo(
            "Ivanov Ivan",
            "Android-developer",
            "In office",
            "https://wampi.ru/image/R9C6OC7",
            "",
            "",
            ""
        ),
        EmployeeInfo(
            "Smirnov Andrey",
            "UI/UX Designer",
            "Will be today",
            "https://www.kasandbox.org/programming-images/avatars/leaf-grey.png",
            "",
            "",
            ""
        ),
        EmployeeInfo(
            "Vasiliev Vasiliy",
            "HR",
            "No bookings",
            "https://www.kasandbox.org/programming-images/avatars/leaf-blue.png",
            "",
            "",
            ""
        ))
    )

    override suspend fun getEmployeesInfo(): MutableStateFlow<List<EmployeeInfo>> {
        //employList.update {  api.getEmployeesInfo().collect(employList)}
        return employList
    }


}
