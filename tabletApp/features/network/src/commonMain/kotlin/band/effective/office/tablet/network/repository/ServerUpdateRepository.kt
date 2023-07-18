package band.effective.office.tablet.network.repository

import kotlinx.coroutines.CoroutineScope

interface ServerUpdateRepository {
    suspend fun subscribeOnUpdates(
        scope: CoroutineScope,
        roomInfoUpdateHandler: () -> Unit,
        organizersListUpdateHandler: () -> Unit
    )
}