package band.effective.office.elevator.ui.main_screem_content

import band.effective.office.elevator.ui.main_screem_content.store.ElevatorStore
import band.effective.office.elevator.ui.main_screem_content.store.ElevatorStoreFactory
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

class MainScreenContentComponent(componentContext: ComponentContext, storeFactory: StoreFactory) :
    ComponentContext by componentContext {

    private val elevatorStore =
        instanceKeeper.getStore {
            ElevatorStoreFactory(
                storeFactory = storeFactory,
            ).create()
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    val state: StateFlow<ElevatorStore.State> = elevatorStore.stateFlow

    val label: Flow<ElevatorStore.Label> = elevatorStore.labels

    fun onEvent(event: ElevatorStore.Intent) {
        elevatorStore.accept(event)
    }
}
