package band.effective.office.tv.repository.leaderIdRepository

import band.effective.office.tv.model.LeaderIdEventInfo
import band.effective.office.tv.network.leader.models.searchEvent.SearchEventsResponse

interface LeaderIdEventsInfoRepository {
    suspend fun getEventsInfo(): List<LeaderIdEventInfo>
}