package band.effective.office.elevator.domain.useCase

import band.effective.office.elevator.data.models.sbp.SBPResponse
import band.effective.office.elevator.domain.repository.SBPRepository
import band.effective.office.elevator.expects.getApplicationBankId
import band.effective.office.elevator.expects.isApplicationInstalled
import band.effective.office.elevator.ui.bottomSheets.sbp.model.SBPBankInfo
import band.effective.office.elevator.ui.bottomSheets.sbp.model.listToDomain
import band.effective.office.network.model.Either
import band.effective.office.network.model.ErrorResponse
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map

class SBPBanksInfoUseCase(
   private val repository: SBPRepository
) {
    suspend fun execute() =
        repository.getBanksInfo()
            .map { it.mapToDomain().filterBankInstalled() }


    private fun Either<ErrorResponse, List<SBPBankInfo>>.filterBankInstalled() =
        when(this) {
            is Either.Error -> this
            is Either.Success -> Either.Success(
                data.filter {
                    val bankId = getApplicationBankId(it)
                    isApplicationInstalled(bankId)
                }
            )
        }

    private fun Either<ErrorResponse, SBPResponse>.mapToDomain(): Either<ErrorResponse, List<SBPBankInfo>> =
        when (this) {
            is Either.Error -> this
            is Either.Success -> Either.Success(
                data.banksInfo.listToDomain()
            )
        }
}