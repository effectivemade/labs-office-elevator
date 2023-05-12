package band.effective.office.tv.repository.notion.impl

import band.effective.office.tv.domain.model.notion.EmployeeInfoEntity
import band.effective.office.tv.network.notion.EmployeeInfoRemoteDataSource
import band.effective.office.tv.repository.notion.EmployeeInfoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EmployeeInfoRepositoryImpl @Inject constructor(private val employeeInfoRemoteDataSource: EmployeeInfoRemoteDataSource) :
    EmployeeInfoRepository {
    override val latestEvents: Flow<List<EmployeeInfoEntity>> = flow {
        val employeesInfo = employeeInfoRemoteDataSource.fetchLatestBirthdays()
        emit(employeesInfo.map { employee ->
            EmployeeInfoEntity(
                employee.firstName.orEmpty(),
                employee.startDate.orEmpty(),
                employee.nextBirthdayDate.orEmpty(),
                employee.photoUrl.orEmpty()
            )
        })
    }
}
