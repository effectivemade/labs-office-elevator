package band.effective.office.tv.network.notion

import band.effective.office.tv.core.network.entity.Either
import kotlinx.coroutines.flow.Flow

interface EmployeeInfoRemoteDataSource {
    suspend fun fetchLatestBirthdays(): Flow<Either<String, List<EmployeeInfoDto>>>
    suspend fun getDuolingoUserName(): List<String>
}