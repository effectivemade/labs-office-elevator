package band.effective.office.tv.useCases

import band.effective.office.tv.domain.models.Employee.EmployeeInfo
import band.effective.office.tv.domain.models.Employee.EmployeeInfoEntity
import band.effective.office.tv.repository.BirthdaysRepositoryImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class BirthdaysUseCase(
    private val repositoryImpl: BirthdaysRepositoryImpl
) {
    fun getLatestBirthdays(): Flow<List<EmployeeInfoEntity>> {
        return repositoryImpl.latestBirthdays
    }
}