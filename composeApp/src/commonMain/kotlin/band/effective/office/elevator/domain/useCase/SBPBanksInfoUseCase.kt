package band.effective.office.elevator.domain.useCase

import band.effective.office.elevator.data.models.sbp.SBPResponse
import band.effective.office.elevator.domain.repository.SBPRepository
import band.effective.office.elevator.ui.bottomSheets.sbp.model.SBPBankInfo
import band.effective.office.elevator.ui.bottomSheets.sbp.model.listToDomain
import band.effective.office.network.model.Either
import band.effective.office.network.model.ErrorResponse
import kotlinx.coroutines.flow.map

class SBPBanksInfoUseCase(
   private val repository: SBPRepository
) {
    suspend fun execute() =
        repository.getBanksInfo().map { it.mapToDomain() }

    private fun Either<ErrorResponse, SBPResponse>.mapToDomain(): Either<ErrorResponse, List<SBPBankInfo>> =
        when (this) {
            is Either.Error -> this
            is Either.Success -> Either.Success(data.banksInfo.listToDomain())
        }
}