package band.effective.office.tv.network.use_cases

import band.effective.office.tv.core.network.entity.Either
import band.effective.office.tv.domain.model.duolingo.DuolingoUser
import band.effective.office.tv.network.notion.EmployeeInfoRemoteDataSource
import band.effective.office.tv.repository.duolingo.DuolingoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

// TODO(Artem Gruzdev) add getting a list user name from notion. And come up with a name for this use case

class DuolingoManager @Inject constructor(
    private val duolingoRepository: DuolingoRepository,
    private val notionData: EmployeeInfoRemoteDataSource
) {
    suspend fun getDuolingoUserinfo(): Flow<Either<String, List<DuolingoUser>>> {
        val duolingoUserNames = notionData.getDuolingoUserName()
        return duolingoRepository.getUsers(duolingoUserNames)
    }
}