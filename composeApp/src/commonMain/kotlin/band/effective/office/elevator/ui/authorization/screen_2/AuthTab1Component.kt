package band.effective.office.elevator.ui.authorization.screen_2

import band.effective.office.elevator.ui.authorization.screen_2.store.AuthTab1Store
import band.effective.office.elevator.ui.authorization.screen_2.store.AuthTab1StoreFactory
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import kotlinx.coroutines.flow.Flow

class AuthTab1Component(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    private val output: (Output) -> Unit
) : ComponentContext by componentContext {

    private val authTab1Store =
        instanceKeeper.getStore {
            AuthTab1StoreFactory(
                storeFactory = storeFactory
            ).create()
        }

    val label: Flow<AuthTab1Store.Label> = authTab1Store.labels


    fun onEvent(event: AuthTab1Store.Intent) {
        authTab1Store.accept(event)
    }

    fun onOutput(output: Output) {
        output(output)
    }

    sealed class Output {
        object OpenTab2Screen : Output()
    }
}
