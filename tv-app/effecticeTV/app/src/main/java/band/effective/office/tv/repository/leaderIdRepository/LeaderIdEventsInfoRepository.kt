package band.effective.office.tv.repository.leaderIdRepository

import band.effective.office.tv.model.LeaderIdEventInfo
import kotlinx.coroutines.flow.Flow

interface LeaderIdEventsInfoRepository {
    suspend fun getEventsInfo(): Flow<LeaderIdEventInfo>
}