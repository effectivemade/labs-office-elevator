package band.effective.office.elevator.ui.bottomSheets.sbp.store

import band.effective.office.elevator.ui.bottomSheets.sbp.model.SBPBankInfo
import com.arkivanov.mvikotlin.core.store.Store

interface SBPStore : Store<SBPStore.Intent, SBPStore.State, Unit> {
    data class State(
        val banks: List<SBPBankInfo>,
        val query: String
    ) {
        companion object {
            val initialState = State(
                banks = listOf(),
                query = ""
            )
        }
    }

    sealed interface Intent {
        data class OnClickBank(val bank: SBPBankInfo) : Intent

        data class OnChangeQuery(val query: String) : Intent
    }
}