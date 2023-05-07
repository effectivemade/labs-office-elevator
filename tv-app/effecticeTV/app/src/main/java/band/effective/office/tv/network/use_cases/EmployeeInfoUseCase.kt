package band.effective.office.tv.network.use_cases

import band.effective.office.tv.domain.models.Employee.EmployeeInfoEntity
import band.effective.office.tv.repository.EmployeeInfoRepositoryImpl
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EmployeeInfoUseCase @Inject constructor(private val repositoryImpl: EmployeeInfoRepositoryImpl) {
    fun getLatestBirthdays(): Flow<List<EmployeeInfoEntity>> {
        return repositoryImpl.latestBirthdays
    }
}