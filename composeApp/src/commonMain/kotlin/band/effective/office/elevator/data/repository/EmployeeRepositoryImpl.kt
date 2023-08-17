package band.effective.office.elevator.data.repository

import band.effective.office.elevator.domain.models.EmployeeInfo
import band.effective.office.elevator.domain.repository.EmployeeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class EmployeeRepositoryImpl(): EmployeeRepository{
    object EmployeesData {
        val initial=listOf(
            EmployeeInfo.defaultEmployee)
    }
    override suspend fun getEmployeesInfo(): Flow<List<EmployeeInfo>> {
        return flow<List<EmployeeInfo>> {
            emit(
                listOf(
                    EmployeeInfo(
                        "1L",
                        "Ivanov Ivan",
                        "Android-developer",
                        "In office",
                        "https://wampi.ru/image/R9C6OC7",
                        "+79137894523",
                        "employee@effective.com",
                        "@Ivanov_Ivan"
                    ),
                    EmployeeInfo(
                        "1H",
                        "Smirnov Andrey",
                        "UI/UX Designer",
                        "Will be today",
                        "https://www.kasandbox.org/programming-images/avatars/leaf-grey.png",
                        "+79263452312",
                        "employee2@effective.com",
                        "@Smirnov_Andrey"
                    ),
                    EmployeeInfo(
                        "1E",
                        "Vasiliev Vasiliy",
                        "HR",
                        "No bookings",
                        "https://www.kasandbox.org/programming-images/avatars/leaf-blue.png",
                        "+79621234434",
                        "employee3@effective.com",
                        "@Vasiliev_Vasiliy"
                    )
                )
            )

        }

    }

}
