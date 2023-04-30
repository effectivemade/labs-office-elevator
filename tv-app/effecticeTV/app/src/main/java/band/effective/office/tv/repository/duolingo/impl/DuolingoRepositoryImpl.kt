package band.effective.office.tv.repository.duolingo.impl

import band.effective.office.tv.core.network.entity.Either
import band.effective.office.tv.domain.model.duolingo.DuolingoUser
import band.effective.office.tv.domain.model.duolingo.toDomain
import band.effective.office.tv.network.duolingo.DuolingoApi
import band.effective.office.tv.repository.duolingo.DuolingoRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DuolingoRepositoryImpl @Inject constructor(
    private val duolingoApi: DuolingoApi,
     private val coroutineScope: CoroutineScope
): DuolingoRepository {
    override suspend fun getUsers(usersName: List<String>): Flow<Either<String, List<DuolingoUser>>> =
        flow {
            val duolingoUsersInfo: MutableList<DuolingoUser> = mutableListOf()
            var errorRequest: String = ""
            usersName.forEach { user ->
                withContext(Dispatchers.IO) {
                    val duolingoUser = duolingoApi.getUserInfo(user)
//                    when (val duolingoUser = duolingoApi.getUserInfo(user)) {
//                        is Either.Success -> {
//                            duolingoUsersInfo.add(
//                                duolingoUser.data.toDomain()
//                            )
//                        }
//                        is Either.Failure -> {errorRequest = duolingoUser.error.message}
//                    }
                }
            }
            emit(
                if(duolingoUsersInfo.isEmpty()) Either.Failure(errorRequest)
                else Either.Success(duolingoUsersInfo.toList())
            )
        }
}
