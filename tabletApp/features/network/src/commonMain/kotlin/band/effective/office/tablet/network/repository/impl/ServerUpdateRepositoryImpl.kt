package band.effective.office.tablet.network.repository.impl

import band.effective.office.tablet.network.api.Api
import band.effective.office.tablet.network.model.WebServerEvent
import band.effective.office.tablet.network.repository.ServerUpdateRepository
import kotlinx.coroutines.CoroutineScope

class ServerUpdateRepositoryImpl(private val api: Api) : ServerUpdateRepository {
    override suspend fun subscribeOnUpdates(
        scope: CoroutineScope,
        roomInfoUpdateHandler: () -> Unit,
        organizersListUpdateHandler: () -> Unit
    ) {
        api.subscribeOnWebHock(scope) { event ->
            when (event) {
                is WebServerEvent.OrganizerInfoUpdate -> organizersListUpdateHandler()
                is WebServerEvent.RoomInfoUpdate -> roomInfoUpdateHandler()
            }
        }
    }
}