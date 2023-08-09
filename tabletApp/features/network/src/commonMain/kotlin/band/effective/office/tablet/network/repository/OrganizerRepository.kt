package band.effective.office.tablet.network.repository

import band.effective.office.network.model.Either
import band.effective.office.tablet.domain.model.ErrorWithData
import band.effective.office.tablet.domain.model.Organizer
import kotlinx.coroutines.flow.Flow

interface OrganizerRepository {
    suspend fun getOrganizersList(): Either<ErrorWithData<List<Organizer>>, List<Organizer>>
    suspend fun subscribeOnUpdates(): Flow<Either<ErrorWithData<List<Organizer>>, List<Organizer>>>
}