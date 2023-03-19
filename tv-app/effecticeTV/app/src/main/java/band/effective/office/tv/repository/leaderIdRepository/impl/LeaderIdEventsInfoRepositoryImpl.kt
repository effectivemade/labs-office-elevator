package band.effective.office.tv.repository.leaderIdRepository.impl

import band.effective.office.tv.domain.model.LeaderIdEventInfo
import band.effective.office.tv.domain.model.toLeaderIdEventInfo
import band.effective.office.tv.core.network.Either
import band.effective.office.tv.core.network.ErrorReason
import band.effective.office.tv.network.leader.LeaderApi
import band.effective.office.tv.repository.leaderIdRepository.LeaderIdEventsInfoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LeaderIdEventsInfoRepositoryImpl @Inject constructor(val leaderApi: LeaderApi) :
    LeaderIdEventsInfoRepository {
    override suspend fun getEventsInfo(count: Int, cityId: Int, placeId: Int): Flow<LeaderIdEventInfo> =
        flow{
            when (val either = leaderApi.searchEvents(count, cityId, placeId)) {
                is Either.Success -> {
                    val ids = either.data.data.items.map { it.id }
                    ids.forEach {
                        emit(
                            when (val apiResponse = leaderApi.eventInfo(it)){
                            is Either.Success -> apiResponse.data.toLeaderIdEventInfo()
                            is Either.Failure -> apiResponse.error.toLeaderIdEventInfo()
                        })
                    }
                }
                is Either.Failure -> emit(either.error.toLeaderIdEventInfo())
            }
        }

}
private fun ErrorReason.toLeaderIdEventInfo(): LeaderIdEventInfo =
    LeaderIdEventInfo(message)






