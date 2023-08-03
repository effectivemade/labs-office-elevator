package band.effective.office.elevator.ui.employee.aboutEmployee

import band.effective.office.elevator.ui.employee.aboutEmployee.store.AboutEmployeeStore
import band.effective.office.elevator.ui.employee.aboutEmployee.store.AboutEmployeeStoreFactory
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow


class AboutEmployeeComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    private val output: (Output) -> Unit
) :
    ComponentContext by componentContext {

    private val aboutEmployeeStore = instanceKeeper.getStore {
        AboutEmployeeStoreFactory(
            storeFactory = storeFactory
        ).create()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val state: StateFlow<AboutEmployeeStore.State> = aboutEmployeeStore.stateFlow

    fun onEvent(event: AboutEmployeeStore.Intent) {
        aboutEmployeeStore.accept(event)
    }
    fun onOutput(output: Output){
        output(output)
    }
    sealed interface Output {
        object OpenMap : Output
        object OpenAllEmployee : Output
    }

}