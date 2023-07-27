package band.effective.office.elevator.domain.usecase

import band.effective.office.elevator.domain.LocationController
import band.effective.office.elevator.domain.models.EmployeeInfo
import band.effective.office.elevator.domain.repository.EmployeeRepository
import band.effective.office.elevator.ui.employee.EmployeeCard
import band.effective.office.elevator.ui.employee.EmployeesData
import kotlinx.coroutines.CoroutineScope

class EmployeeUseCase(
    private val repository: EmployeeRepository,
    private val bookingUseCase: BookingUseCase,
    private val locationController: LocationController
) {

    suspend operator fun invoke(employeeInfo: EmployeeInfo)=repository.getEmployeesInfo(employeeInfo)

    suspend fun getUsersResearches()=bookingUseCase//()

    suspend fun getEmployeesLocations()=locationController//()

    fun subscribe(scope: CoroutineScope, handler: (EmployeeInfo) -> Unit) {
        repository.subscribeOnEmployeesInfoUpdates(scope) { handler(it) }
    }

     fun changeEmployeeShowedList(query: String): List<EmployeeCard> {
        return if(query.isEmpty()){
            EmployeesData.employeesCardData
        }else {
            var compareString = query.filter { !it.isWhitespace() }.lowercase()
            val allEmployeesCards = EmployeesData.employeesCardData

            var showedEmployeesCards  = allEmployeesCards.filter { it.name.filter { !it.isWhitespace() }
                .lowercase().contains(compareString) }


            showedEmployeesCards
        }
    }
}