package band.effective.office.elevator.ui.authorization

import band.effective.office.elevator.ui.authorization.store.AuthorizationStore
import band.effective.office.elevator.ui.authorization.store.AuthorizationStoreFactory
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import kotlinx.coroutines.flow.Flow

class AuthorizationComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    private val output: (Output) -> Unit
) : ComponentContext by componentContext {

    private val authorizationStore =
        instanceKeeper.getStore {
            AuthorizationStoreFactory(
                storeFactory = storeFactory
            ).create()
        }

    val label: Flow<AuthorizationStore.Label> = authorizationStore.labels


    fun onEvent(event: AuthorizationStore.Intent) {
        authorizationStore.accept(event)
    }

    fun onOutput(output: Output) {
        output(output)
    }

    sealed class Output {
        object OpenAuthorizationPhoneScreen : Output()
//        object OpenMainScreen : Output()
    }

}
