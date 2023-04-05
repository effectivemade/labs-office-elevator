package band.effective.office.tv.useCases

import band.effective.office.tv.domain.models.Employee.EmployeeInfoEntity
import band.effective.office.tv.repository.EmployeeInfoRepositoryImpl
import kotlinx.coroutines.flow.Flow

class EmployeeInfoUseCase(
    private val repositoryImpl: EmployeeInfoRepositoryImpl
) {
    fun getLatestBirthdays(): Flow<List<EmployeeInfoEntity>> {
        return repositoryImpl.latestBirthdays
    }
}