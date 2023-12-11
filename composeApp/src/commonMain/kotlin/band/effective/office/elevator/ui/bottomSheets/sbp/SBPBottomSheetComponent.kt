package band.effective.office.elevator.ui.bottomSheets.sbp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import band.effective.office.elevator.ui.bottomSheets.BottomSheet
import band.effective.office.elevator.ui.bottomSheets.sbp.store.SBPSoreFactory
import band.effective.office.elevator.ui.bottomSheets.sbp.store.SBPStore
import band.effective.office.elevator.ui.employee.aboutEmployee.store.AboutEmployeeStoreFactory
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi

class SBPBottomSheetComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    userPhoneNumber: String
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
            banks = state.banks,
            query = state.query,
            onQueryUpdate = { store.accept(SBPStore.Intent.OnChangeQuery(it)) },
            onClickBank = { store.accept(SBPStore.Intent.OnClickBank(it)) }
        )
    }

    @Composable
    override fun content() {
    }
}