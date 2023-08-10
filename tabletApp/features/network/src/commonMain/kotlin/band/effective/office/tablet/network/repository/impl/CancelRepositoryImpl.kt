package band.effective.office.tablet.network.repository.impl

import band.effective.office.network.api.Api
import band.effective.office.network.model.Either
import band.effective.office.network.model.ErrorResponse
import band.effective.office.tablet.domain.model.EventInfo
import band.effective.office.tablet.network.repository.CancelRepository
import band.effective.office.tablet.utils.map

class CancelRepositoryImpl(private val api: Api) :
    CancelRepository {
    override suspend fun cancelEvent(eventInfo: EventInfo): Either<ErrorResponse, String> =
        api.deleteBooking(eventInfo.id).map({ it }, { it.status })
}

