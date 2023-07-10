package band.effective.office.elevator.ui.main

import band.effective.office.elevator.ui.main.store.MainStore
import band.effective.office.elevator.ui.main.store.MainStoreFactory
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

class MainComponent(componentContext: ComponentContext, storeFactory: StoreFactory) :
    ComponentContext by componentContext {

    private val elevatorStore =
        instanceKeeper.getStore {
            MainStoreFactory(
                storeFactory = storeFactory,
            ).create()
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    val state: StateFlow<MainStore.State> = elevatorStore.stateFlow

    val label: Flow<MainStore.Label> = elevatorStore.labels

    fun onEvent(event: MainStore.Intent) {
        elevatorStore.accept(event)
    }
}
