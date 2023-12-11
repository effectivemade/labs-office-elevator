package band.effective.office.elevator.ui.employee.aboutEmployee

import band.effective.office.elevator.domain.models.EmployeeInfo
import band.effective.office.elevator.ui.booking.BookingComponent
import band.effective.office.elevator.ui.bottomSheets.BottomSheet
import band.effective.office.elevator.ui.bottomSheets.sbp.SBPBottomSheetComponent
import band.effective.office.elevator.ui.employee.aboutEmployee.store.AboutEmployeeStore
import band.effective.office.elevator.ui.employee.aboutEmployee.store.AboutEmployeeStoreFactory
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.slot.dismiss
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow


class AboutEmployeeComponent(
    componentContext: ComponentContext,
    private val storeFactory: StoreFactory,
    private val output: (Output) -> Unit,
    employee: EmployeeInfo
) :
    ComponentContext by componentContext {

    private val aboutEmployeeStore = instanceKeeper.getStore {
        AboutEmployeeStoreFactory(
            storeFactory = storeFactory,
            employeeInfo = employee
        ).create()
    }
    private val navigation = SlotNavigation<AboutEmployeeComponent.SheetConfig>()
    val slot: Value<ChildSlot<AboutEmployeeComponent.SheetConfig, BottomSheet>> = childSlot(
        source = navigation,
        childFactory = ::slotFactory
    )

    private fun slotFactory(
        sheetConfig: SheetConfig,
        componentContext: ComponentContext
    ): BottomSheet =
        when(sheetConfig) {
            is SheetConfig.BanksList -> SBPBottomSheetComponent(
                componentContext = componentContext,
                storeFactory = storeFactory,
                userPhoneNumber = sheetConfig.userPhoneNumber
            )
        }


        @OptIn(ExperimentalCoroutinesApi::class)
    val state: StateFlow<AboutEmployeeStore.State> = aboutEmployeeStore.stateFlow
    val label: Flow<AboutEmployeeStore.Label> = aboutEmployeeStore.labels

    fun onEvent(event: AboutEmployeeStore.Intent) {
        aboutEmployeeStore.accept(event)
    }
    fun onOutput(output: Output){
        output(output)
    }


    sealed interface Output {
        object OpenAllEmployee : Output
    }

    sealed interface SheetConfig : Parcelable {
        @Parcelize
        data class BanksList(val userPhoneNumber: String) : SheetConfig
    }

    fun closeSheet() {
        navigation.dismiss()
    }

    fun openSheet(config: SheetConfig) {
        navigation.activate(config)
    }
}