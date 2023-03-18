package band.effective.office.tv.repository.leaderIdRepository

import band.effective.office.tv.model.LeaderIdEventInfo
import kotlinx.coroutines.flow.Flow

interface LeaderIdEventsInfoRepository {
    suspend fun getEventsInfo(count: Int = 10, cityId: Int = 893, placeId: Int = 3942): Flow<LeaderIdEventInfo>
}