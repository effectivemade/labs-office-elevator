package band.effective.office.tvServer.service

import band.effective.office.tvServer.repository.leaderId.LeaderIdEventInfo
import band.effective.office.tvServer.repository.leaderId.LeaderRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

class LeaderIdService(private val leaderRepository: LeaderRepository) {
    suspend fun getEvents(
        finishDate: LocalDate? = null,
        cityId: Int? = null,
        placeId: Int? = null
    ): Flow<LeaderIdEventInfo> = leaderRepository.getEventsInfo(
        finishDate = finishDate ?: LocalDate.now().plusDays(31),
        cityId = cityId ?: 893,
        placeId = placeId ?: 3942
    )

}