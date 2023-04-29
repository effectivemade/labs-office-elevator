package band.effective.office.tv.network.use_cases

import band.effective.office.tv.core.network.entity.Either
import band.effective.office.tv.domain.model.duolingo.DuolingoUser
import band.effective.office.tv.repository.duolingo.DuolingoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

// TODO(Artem Gruzdev) add getting a list user name from notion. And come up with a name for this use case

class DuolingoManager @Inject constructor(
    private val duolingoRepository: DuolingoRepository
) {
    suspend fun getDuolingoUserinfo(): Flow<Either<String, List<DuolingoUser>>> =
        duolingoRepository.getUsers(
            listOf("daiki835234", "000412.c47nqLLM", "ph.QoO1Qq", "8ufC9")
        )
}