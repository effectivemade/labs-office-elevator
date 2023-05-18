package band.effective.office.tv.repository.notion

import band.effective.office.tv.domain.model.notion.EmployeeInfoEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

@Singleton
interface EmployeeInfoRepository {
    val latestEvents: Flow<List<EmployeeInfoEntity>>
}