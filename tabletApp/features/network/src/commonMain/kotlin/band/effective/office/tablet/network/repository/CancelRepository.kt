package band.effective.office.tablet.network.repository

import band.effective.office.network.model.Either
import band.effective.office.network.model.ErrorResponse
import band.effective.office.tablet.domain.model.EventInfo

/**Repository for cancel event*/
interface CancelRepository {
    /**Cancel event
     * @param eventInfo canceled event
     * @return if cancel booking return "ok", else return ErrorResponse*/
    suspend fun cancelEvent(eventInfo: EventInfo): Either<ErrorResponse, String>
}