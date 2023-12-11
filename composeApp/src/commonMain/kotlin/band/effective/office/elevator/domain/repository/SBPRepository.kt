package band.effective.office.elevator.domain.repository

import band.effective.office.elevator.data.models.sbp.SBPResponse
import band.effective.office.network.model.Either
import band.effective.office.network.model.ErrorResponse
import kotlinx.coroutines.flow.Flow

interface SBPRepository {
    suspend fun getBanksInfo() : Flow<Either<ErrorResponse, SBPResponse>>
}