package band.effective.office.tablet.network.repository

import band.effective.office.tablet.domain.model.Either
import band.effective.office.tablet.domain.model.ErrorWithData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job

interface OrganizerRepository {
    suspend fun getOrganizersList(): Either<ErrorWithData<List<String>>, List<String>>
    fun subscribeOnUpdates(scope: CoroutineScope, handler: (Either<ErrorWithData<List<String>>, List<String>>) -> Unit): Job
}