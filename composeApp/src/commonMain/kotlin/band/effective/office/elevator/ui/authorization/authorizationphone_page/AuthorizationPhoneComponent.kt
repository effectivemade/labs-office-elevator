package band.effective.office.elevator.ui.authorization.authorizationphone_page

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import kotlinx.coroutines.flow.Flow

class AuthorizationPhoneComponent(
    componentContext: ComponentContext,
    private val storeFactory: StoreFactory,
    private val output: (AuthorizationPhoneComponent.Output) -> Unit
) : ComponentContext by componentContext {

    private val authorizationPhoneStore =
        instanceKeeper.getStore {
            AuthorizationPhoneStoreFactory(
                storeFactory = storeFactory
            ).create()
        }

    val label: Flow<AuthorizationPhoneStore.Label> = authorizationPhoneStore.labels

    fun onEvent(event: AuthorizationPhoneStore.Intent) {
        authorizationPhoneStore.accept(event)
    }

    fun onOutput(output: Output) {
        output(output)
    }

    sealed class Output {
        object OpenProfileScreen : Output()
    }
}
