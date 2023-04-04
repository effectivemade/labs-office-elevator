package band.effective.office.tv.repository.leaderIdRepository

import band.effective.office.tv.core.network.Either
import band.effective.office.tv.domain.model.LeaderIdEventInfo
import kotlinx.coroutines.flow.Flow
import java.util.*

interface LeaderIdEventsInfoRepository {
    suspend fun getEventsInfo(finishDate: GregorianCalendar, cityId: Int = 893, placeId: Int = 3942): Flow<Either<String, LeaderIdEventInfo>>
}