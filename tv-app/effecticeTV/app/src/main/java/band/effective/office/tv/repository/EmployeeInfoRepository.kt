package band.effective.office.tv.repository

import band.effective.office.tv.domain.models.Employee.EmployeeInfoEntity
import kotlinx.coroutines.flow.Flow

interface EmployeeInfoRepository {
    val latestEvents: Flow<List<EmployeeInfoEntity>>
}