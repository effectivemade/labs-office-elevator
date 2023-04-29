package band.effective.office.tv.repository.duolingo

import band.effective.office.tv.core.network.entity.Either
import band.effective.office.tv.domain.model.duolingo.DuolingoUser
import kotlinx.coroutines.flow.Flow

interface DuolingoRepository {
    suspend fun getUsers(usersName: List<String>): Flow<Either<String, List<DuolingoUser>>>
}