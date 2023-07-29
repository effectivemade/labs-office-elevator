package band.effective.office.tablet.network.repository.impl

import band.effective.office.tablet.domain.model.Either
import band.effective.office.tablet.network.api.Api
import band.effective.office.tablet.network.model.WebServerEvent
import band.effective.office.tablet.network.repository.OrganizerRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import network.model.ErrorResponse

class OrganizerRepositoryImpl(private val api: Api) : OrganizerRepository {
    private val orgList =
        MutableStateFlow<Either<ErrorResponse, List<String>>>(Either.Success(listOf()))

    override suspend fun getOrganizersList(): Either<ErrorResponse, List<String>> =
        api.getOrganizers()

    override fun subscribeOnUpdates(
        scope: CoroutineScope,
        handler: (Either<ErrorResponse, List<String>>) -> Unit
    ): Job =
        scope.launch(Dispatchers.IO) {
            orgList.update { getOrganizersList() }
            api.subscribeOnWebHock(scope) {
                if (it is WebServerEvent.OrganizerInfoUpdate)
                    launch(Dispatchers.IO) { orgList.update { getOrganizersList() } }
            }
            orgList.collect { handler(it) }
        }
}