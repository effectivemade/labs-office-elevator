package band.effective.office.elevator.ui.bottomSheets.sbp.store

import band.effective.office.elevator.ui.bottomSheets.sbp.model.SBPBankInfo
import com.arkivanov.mvikotlin.core.store.Store

interface SBPStore : Store<SBPStore.Intent, SBPStore.State, Unit> {
    data class State(
        val showingBanks: List<SBPBankInfo>,
        val allBanks: List<SBPBankInfo>,
        val query: String
    ) {
        companion object {
            val initialState = State(
                showingBanks = listOf(),
                query = "",
                allBanks = listOf()
            )
        }
    }

    sealed interface Intent {
        data class OnClickBank(val bank: SBPBankInfo) : Intent

        data class OnChangeQuery(val query: String) : Intent
    }
}