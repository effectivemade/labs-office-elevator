package band.effective.office.elevator.data.repository

import band.effective.office.elevator.data.models.sbp.SBPResponse
import band.effective.office.elevator.domain.repository.SBPRepository
import band.effective.office.network.model.Either
import band.effective.office.network.model.ErrorResponse
import band.effective.office.utils.KtorEtherClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


//TODO (Artem Gruzdev) write custom httpClient
class SBPRepositoryImpl: SBPRepository {
    override suspend fun getBanksInfo(): Flow<Either<ErrorResponse, SBPResponse>> = flow {
        val banksInfo: Either<ErrorResponse, SBPResponse> = KtorEtherClient.securityResponse(
            urlString = "https://qr.nspk.ru/proxyapp/c2bmembers.json"
        )
        emit(banksInfo)
    }
}