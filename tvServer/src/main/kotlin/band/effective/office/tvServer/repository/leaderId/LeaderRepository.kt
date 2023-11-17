package band.effective.office.tvServer.repository.leaderId

import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.time.LocalDateTime

interface LeaderRepository {
    suspend fun getEventsInfo(finishDate: LocalDate, cityId: Int, placeId: Int): Flow<LeaderIdEventInfo>
}
data class LeaderIdEventInfo(
    val id: Int,
    val name: String,
    val startDateTime: LocalDateTime,
    val finishDateTime: LocalDateTime,
    val isOnline: Boolean,
    val photoUrl: String?,
    val organizer: String?,
    val speakers: List<String>?,
    val endRegDate: LocalDateTime?
)