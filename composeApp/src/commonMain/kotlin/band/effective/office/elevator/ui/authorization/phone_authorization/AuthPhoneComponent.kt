package band.effective.office.elevator.ui.authorization.phone_authorization

import band.effective.office.elevator.ui.authorization.phone_authorization.store.AuthPhoneStore
import band.effective.office.elevator.ui.authorization.phone_authorization.store.AuthPhoneStoreFactory
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import kotlinx.coroutines.flow.Flow

class AuthPhoneComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    private val output: (Output) -> Unit,
    phone: String,
) : ComponentContext by componentContext {

    private val authTab1Store =
        instanceKeeper.getStore {
            AuthPhoneStoreFactory(
                storeFactory = storeFactory
            ).create()
        }

    val label: Flow<AuthPhoneStore.Label> = authTab1Store.labels


    fun onEvent(event: AuthPhoneStore.Intent) {
        authTab1Store.accept(event)
    }

    fun onOutput(output: Output) {
        output(output)
    }

    sealed class Output {
        object OpenAuthProfileScreen : Output()

    }

}
