package band.effective.office.tablet.network.repository

import band.effective.office.network.model.Either
import band.effective.office.network.model.ErrorResponse
import band.effective.office.tablet.domain.model.EventInfo

interface CancelRepository {
    suspend fun cancelEvent(eventInfo: EventInfo): Either<ErrorResponse, String>
}