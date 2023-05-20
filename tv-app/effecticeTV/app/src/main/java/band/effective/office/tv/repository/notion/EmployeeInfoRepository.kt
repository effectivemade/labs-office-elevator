package band.effective.office.tv.repository.notion

import band.effective.office.tv.core.network.entity.Either
import band.effective.office.tv.domain.model.notion.EmployeeInfoEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

@Singleton
interface EmployeeInfoRepository {
    fun latestEvents(): Flow<Either<String, List<EmployeeInfoEntity>>>
}