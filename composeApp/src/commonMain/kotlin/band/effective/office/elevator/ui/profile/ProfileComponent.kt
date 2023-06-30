package band.effective.office.elevator.ui.profile

import band.effective.office.elevator.ui.profile.store.ProfileStore
import band.effective.office.elevator.ui.profile.store.ProfileStoreFactory
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

class ProfileComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    private val output: (Output) -> Unit
) :
    ComponentContext by componentContext {

    private val profileStore = instanceKeeper.getStore {
        ProfileStoreFactory(
            storeFactory = storeFactory
        ).create()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val state: StateFlow<ProfileStore.State> = profileStore.stateFlow

    val label: Flow<ProfileStore.Label> = profileStore.labels

    fun onEvent(event: ProfileStore.Intent) {
        profileStore.accept(event)
    }

    fun onOutput(output: Output) {
        output(output)
    }

    sealed interface Output {
        object OpenAuthorizationFlow : Output
    }

}