package band.effective.office.tablet.network.repository.impl

import band.effective.office.tablet.network.api.Api
import band.effective.office.tablet.network.model.WebServerEvent
import band.effective.office.tablet.network.repository.OrganizerRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OrganizerRepositoryImpl(private val api: Api) : OrganizerRepository {
    private val orgList = MutableStateFlow(listOf<String>())
    override suspend fun getOrganizersList(): List<String> = api.getOrganizers()
    override fun subscribeOnUpdates(scope: CoroutineScope, handler: (List<String>) -> Unit): Job =
        scope.launch(Dispatchers.IO) {
            orgList.update { getOrganizersList() }
            api.subscribeOnWebHock(scope) {
                if (it is WebServerEvent.OrganizerInfoUpdate)
                    launch(Dispatchers.IO) { orgList.update { getOrganizersList() } }
            }
            orgList.collect { handler(it) }
        }
}