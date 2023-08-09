package band.effective.office.tablet.network.repository

import band.effective.office.network.model.Either
import band.effective.office.network.model.ErrorResponse
import band.effective.office.tablet.domain.model.ErrorWithData
import band.effective.office.tablet.domain.model.RoomInfo
import kotlinx.coroutines.flow.Flow

interface RoomRepository {
    suspend fun getRoomInfo(room: String): Either<ErrorWithData<RoomInfo>, RoomInfo>
    suspend fun getRoomsInfo(): Either<ErrorResponse, List<RoomInfo>>
    fun subscribeOnUpdates(
        roomId: String
    ): Flow<Either<ErrorWithData<RoomInfo>, RoomInfo>>
}