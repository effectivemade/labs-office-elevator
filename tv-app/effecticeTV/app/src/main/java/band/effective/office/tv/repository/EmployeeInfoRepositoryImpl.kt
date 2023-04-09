package band.effective.office.tv.repository

import band.effective.office.tv.domain.models.Employee.EmployeeInfoEntity
import band.effective.office.tv.source.EmployeeInfoRemoteDataSource
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
        employeesInfo.map {
            result.add(
                EmployeeInfoEntity(
                    it.firstName ?: throw NullPointerException(),
                    it.startDate ?: throw NullPointerException(),
                    it.nextBirthdayDate ?: throw NullPointerException(),
                    it.photoUrl ?: throw NullPointerException()
                )
            )
        }
        emit(result)
    }
}
