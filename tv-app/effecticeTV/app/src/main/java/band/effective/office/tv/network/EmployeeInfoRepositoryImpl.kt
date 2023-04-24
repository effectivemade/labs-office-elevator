package band.effective.office.tv.network

import band.effective.office.tv.domain.models.Employee.EmployeeInfoEntity
import band.effective.office.tv.domain.models.Employee.EmployeeInfoRepository
import band.effective.office.tv.network.notion.EmployeeInfoRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EmployeeInfoRepositoryImpl @Inject constructor(private val employeeInfoRemoteDataSource: EmployeeInfoRemoteDataSource) :
    EmployeeInfoRepository {
    override val latestEvents: Flow<List<EmployeeInfoEntity>> = flow {
        val employeesInfo = employeeInfoRemoteDataSource.fetchLatestBirthdays()
        val result = mutableListOf<EmployeeInfoEntity>()
        employeesInfo.map { employee ->
            result.add(
                EmployeeInfoEntity(
                    employee.firstName ?: throw NullPointerException(),
                    employee.startDate ?: throw NullPointerException(),
                    employee.nextBirthdayDate ?: throw NullPointerException(),
                    employee.photoUrl ?: throw NullPointerException()
                )
            )
        }
        emit(result)
    }
}
