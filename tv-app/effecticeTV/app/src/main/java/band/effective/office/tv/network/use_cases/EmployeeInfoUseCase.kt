package band.effective.office.tv.network.use_cases

import band.effective.office.tv.domain.models.Employee.EmployeeInfoEntity
import band.effective.office.tv.repository.EmployeeInfoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EmployeeInfoUseCase @Inject constructor(private val repository: EmployeeInfoRepository) {
    fun getLatestBirthdays(): Flow<List<EmployeeInfoEntity>> {
        return repository.latestEvents
    }
}