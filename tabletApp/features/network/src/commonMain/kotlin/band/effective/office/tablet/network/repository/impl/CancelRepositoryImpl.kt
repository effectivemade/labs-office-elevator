package band.effective.office.tablet.network.repository.impl

import band.effective.office.tablet.domain.model.Either
import band.effective.office.tablet.network.api.Api
import band.effective.office.tablet.network.repository.CancelRepository
import network.model.ErrorResponse

class CancelRepositoryImpl(private val api: Api) : CancelRepository {
    override suspend fun cancelEvent(): Either<ErrorResponse, String> = api.cancelEvent()
}