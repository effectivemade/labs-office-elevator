package band.effective.office.elevator.ui.authorization.tg_authorization

import band.effective.office.elevator.ui.authorization.profile_authorization.store.AuthProfileStore
import band.effective.office.elevator.ui.authorization.profile_authorization.store.AuthProfileStoreFactory
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import kotlinx.coroutines.flow.Flow

class AuthTGComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    private val output: (Output) -> Unit,
    nick: String
) : ComponentContext by componentContext {

    private val authProfileStore =
        instanceKeeper.getStore {
            AuthProfileStoreFactory(
                storeFactory = storeFactory
            ).create()
        }

    val label: Flow<AuthProfileStore.Label> = authProfileStore.labels


    fun onEvent(event: AuthProfileStore.Intent) {
        authProfileStore.accept(event)
    }

    fun onOutput(output: Output) {
        output(output)
    }

    sealed class Output {
        object OpenMainScreen : Output()
    }
}
