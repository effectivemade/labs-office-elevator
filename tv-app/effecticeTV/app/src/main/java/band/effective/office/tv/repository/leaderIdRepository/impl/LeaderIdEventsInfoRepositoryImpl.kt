package band.effective.office.tv.repository.leaderIdRepository.impl

import band.effective.office.tv.model.LeaderIdEventInfo
import band.effective.office.tv.model.toLeaderIdEventInfo
import band.effective.office.tv.network.core.Either
import band.effective.office.tv.network.leader.LeaderApi
import band.effective.office.tv.repository.leaderIdRepository.LeaderIdEventsInfoRepository
import javax.inject.Inject

class LeaderIdEventsInfoRepositoryImpl @Inject constructor(val leaderApi: LeaderApi) :
    LeaderIdEventsInfoRepository {
    override suspend fun getEventsInfo(): List<LeaderIdEventInfo> =
        when (val either = leaderApi.searchEvents(count = 10, cityId = 893, placeId = 3942)) {
            is Either.Success -> {
                val ids = either.data.data.items.map { it.id }
                val list: MutableList<LeaderIdEventInfo> = mutableListOf()
                ids.forEach {
                    val apiResponse = leaderApi.eventInfo(it)
                    if (apiResponse is Either.Success) {
                        list.add(apiResponse.data.toLeaderIdEventInfo())
                    }
                }
                list
            }
            is Either.Failure -> listOf()
        }
}






