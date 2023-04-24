package band.effective.office.tv.domain.models.Employee

import band.effective.office.tv.domain.models.Employee.EmployeeInfoEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

@Singleton
interface EmployeeInfoRepository {
    val latestEvents: Flow<List<EmployeeInfoEntity>>
}