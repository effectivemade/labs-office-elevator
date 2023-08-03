package band.effective.office.tablet.network.repository.impl

import band.effective.office.tablet.domain.model.Either
import band.effective.office.tablet.domain.model.ErrorWithData
import band.effective.office.tablet.network.api.Api
import band.effective.office.tablet.network.model.WebServerEvent
import band.effective.office.tablet.network.repository.OrganizerRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OrganizerRepositoryImpl(private val api: Api) : OrganizerRepository {
    private val orgList =
        MutableStateFlow<Either<ErrorWithData<List<String>>, List<String>>?>(null)

    private suspend fun loadOrganizersList(): Either<ErrorWithData<List<String>>, List<String>> {
        when (val response = api.getOrganizers()) {
            is Either.Error -> orgList.update {
                Either.Error(
                    ErrorWithData(
                        error = response.error, saveData = when (it) {
                            is Either.Error -> it.error.saveData
                            is Either.Success -> it.data
                            null -> null
                        }
                    )
                )
            }

            is Either.Success -> orgList.update { response }
        }
        return orgList.value!!
    }

    override suspend fun getOrganizersList(): Either<ErrorWithData<List<String>>, List<String>> =
        if (orgList.value != null) orgList.value!! else loadOrganizersList()

    override fun subscribeOnUpdates(
        scope: CoroutineScope,
        handler: (Either<ErrorWithData<List<String>>, List<String>>) -> Unit
    ): Job =
        scope.launch(Dispatchers.IO) {
            orgList.update { getOrganizersList() }
            api.subscribeOnWebHock(scope) {
                if (it is WebServerEvent.OrganizerInfoUpdate)
                    launch(Dispatchers.IO) { orgList.update { getOrganizersList() } }
            }
            orgList.collect { if (it != null) handler(it) }
        }
}