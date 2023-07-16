package band.effective.office.elevator.ui.authorization.authorization_google

import band.effective.office.elevator.ui.authorization.authorization_google.store.AuthorizationGoogleStore
import band.effective.office.elevator.ui.authorization.authorization_google.store.AuthorizationGoogleStoreFactory
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import kotlinx.coroutines.flow.Flow

class AuthorizationGoogleComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    private val output: (Output) -> Unit
) : ComponentContext by componentContext {

    private val authorizationStore =
        instanceKeeper.getStore {
            AuthorizationGoogleStoreFactory(
                storeFactory = storeFactory
            ).create()
        }

    val label: Flow<AuthorizationGoogleStore.Label> = authorizationStore.labels


    fun onEvent(event: AuthorizationGoogleStore.Intent) {
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
