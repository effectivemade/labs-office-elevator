package band.effective.office.tv.repository.leaderIdRepository.impl

import band.effective.office.tv.network.core.Either
import band.effective.office.tv.network.leader.LeaderApi
import band.effective.office.tv.network.leader.models.searchEvent.SearchEventsResponse
import band.effective.office.tv.repository.leaderIdRepository.LeaderIdEventsInfoRepository
import javax.inject.Inject

class LeaderIdEventsInfoRepositoryImpl @Inject constructor(val leaderApi: LeaderApi): LeaderIdEventsInfoRepository {
    override suspend fun getEventsInfo(): SearchEventsResponse? {

        val either = leaderApi.searchEvents(10,893,3942)
        return when (either){
            is Either.Success -> either.data
            is Either.Failure -> null
        }
    }
}