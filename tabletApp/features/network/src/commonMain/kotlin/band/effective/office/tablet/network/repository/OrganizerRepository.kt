package band.effective.office.tablet.network.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job

interface OrganizerRepository {
    suspend fun getOrganizersList(): List<String>
    fun subscribeOnUpdates(scope: CoroutineScope, handler: (List<String>) -> Unit): Job
}