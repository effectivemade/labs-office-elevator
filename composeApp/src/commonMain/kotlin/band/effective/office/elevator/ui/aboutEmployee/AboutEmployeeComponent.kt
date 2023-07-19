package band.effective.office.elevator.ui.aboutEmployee

import band.effective.office.elevator.ui.aboutEmployee.store.AboutEmployeeStore
import band.effective.office.elevator.ui.aboutEmployee.store.AboutEmployeeStoreFactory
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow


class AboutEmployeeComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory) :
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
        when(output) {
            is Output.OpenMap -> TODO()
        }
    }
    sealed interface Output {
        object OpenMap : Output
    }

}