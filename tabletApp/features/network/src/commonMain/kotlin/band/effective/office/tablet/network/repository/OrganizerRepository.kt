package band.effective.office.tablet.network.repository

import band.effective.office.tablet.domain.model.Either
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import network.model.ErrorResponse

interface OrganizerRepository {
    suspend fun getOrganizersList(): Either<ErrorResponse, List<String>>
    fun subscribeOnUpdates(scope: CoroutineScope, handler: (Either<ErrorResponse, List<String>>) -> Unit): Job
}