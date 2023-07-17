package band.effective.office.tablet.network.repository

import band.effective.office.tablet.domain.model.RoomInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job

interface RoomRepository {
    suspend fun getRoomInfo(): RoomInfo
    fun subscribeOnUpdates(scope: CoroutineScope, handler: (RoomInfo) -> Unit): Job
}