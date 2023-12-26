package band.effective.office.elevator.ui.bottomSheets.sbp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import band.effective.office.elevator.ui.bottomSheets.BottomSheet
import band.effective.office.elevator.ui.bottomSheets.sbp.store.SBPSoreFactory
import band.effective.office.elevator.ui.bottomSheets.sbp.store.SBPStore
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi

class SBPBottomSheetComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    userPhoneNumber: String,
    private val onClickClose: () -> Unit
) : BottomSheet, ComponentContext by componentContext {

    private val store = instanceKeeper.getStore {
        SBPSoreFactory(
            storeFactory = storeFactory,
            userPhoneNumber = userPhoneNumber
        ).create()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Composable
    override fun SheetContent() {
        val state by store.stateFlow.collectAsState()
        SBPSheet(
            banks = state.showingBanks,
            query = state.query,
            onQueryUpdate = { store.accept(SBPStore.Intent.OnChangeQuery(it)) },
            onClickBank = { store.accept(SBPStore.Intent.OnClickBank(it)) },
            onClickBack = onClickClose
        )
    }

    @Composable
    override fun content() {
    }
}