package band.effective.office.tv.repository.leaderIdRepository

import band.effective.office.tv.model.LeaderIdEventInfo

interface LeaderIdEventsInfoRepository {
    suspend fun getEventsInfo(): List<LeaderIdEventInfo>
}