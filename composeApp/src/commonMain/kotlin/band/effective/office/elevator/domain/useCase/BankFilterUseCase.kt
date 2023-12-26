package band.effective.office.elevator.domain.useCase

import band.effective.office.elevator.ui.bottomSheets.sbp.model.SBPBankInfo

class BankFilterUseCase() {
    fun execute(banksList: List<SBPBankInfo>, query: String) : List<SBPBankInfo> =
        banksList.filter {
            it.bankName.contains(query, ignoreCase = true)
        }
}