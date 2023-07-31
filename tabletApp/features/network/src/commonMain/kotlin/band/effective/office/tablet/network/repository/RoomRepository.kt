package band.effective.office.tablet.network.repository

import band.effective.office.tablet.domain.model.Either
import band.effective.office.tablet.domain.model.RoomInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import network.model.ErrorResponse

interface RoomRepository {
    suspend fun getRoomInfo(): Either<ErrorResponse, RoomInfo>
    fun subscribeOnUpdates(scope: CoroutineScope, handler: (Either<ErrorResponse, RoomInfo>) -> Unit): Job
}