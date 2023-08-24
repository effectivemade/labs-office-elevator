package band.effective.office.tablet.network.repository.impl

import band.effective.office.network.api.Api
import band.effective.office.network.dto.UserDTO
import band.effective.office.network.model.Either
import band.effective.office.network.model.ErrorResponse
import band.effective.office.tablet.domain.model.ErrorWithData
import band.effective.office.tablet.domain.model.Organizer
import band.effective.office.tablet.network.repository.OrganizerRepository
import band.effective.office.tablet.utils.Converter.toOrganizer
import band.effective.office.tablet.utils.map
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OrganizerRepositoryImpl(private val api: Api) : OrganizerRepository {

    private var orgList: MutableStateFlow<Either<ErrorWithData<List<Organizer>>, List<Organizer>>> =
        MutableStateFlow(
            Either.Error(
                ErrorWithData<List<Organizer>>(
                    ErrorResponse(0, ""), null
                )
            )
        )

    private suspend fun loadOrganizersList(): Either<ErrorWithData<List<Organizer>>, List<Organizer>> =
        with(orgList.value) {
            orgList.update {
                api.getUsers(tag = "employee").convert(this)
            }
            orgList.value
        }

    override suspend fun getOrganizersList(): Either<ErrorWithData<List<Organizer>>, List<Organizer>> =
        with(orgList.value) {
            if (this is Either.Error && error.error.code == 0) loadOrganizersList()
            else {
                coroutineScope { launch { loadOrganizersList() } }
                orgList.value
            }
        }


    override fun subscribeOnUpdates(scope: CoroutineScope): Flow<Either<ErrorWithData<List<Organizer>>, List<Organizer>>> =
        flow {
            emit(loadOrganizersList())
            api.subscribeOnOrganizersList(scope)
                .collect { emit(it.convert(orgList.value).apply { orgList.update { this } }) }
        }

    private fun Either<ErrorResponse, List<UserDTO>>.convert(oldValue: Either<ErrorWithData<List<Organizer>>, List<Organizer>>) =
        map(errorMapper = { error ->
            ErrorWithData(
                error = error, saveData = when (oldValue) {
                    is Either.Error -> oldValue.error.saveData
                    is Either.Success -> oldValue.data
                }
            )
        },
            successMapper = { user ->
                user.filter { it.tag == "employee" }.map { it.toOrganizer() }
            })
}
