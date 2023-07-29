package band.effective.office.tablet.network.repository

import band.effective.office.tablet.domain.model.Either
import network.model.ErrorResponse

interface CancelRepository {
    suspend fun cancelEvent(): Either<ErrorResponse, String>
}