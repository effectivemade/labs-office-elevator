package band.effective.office.elevator.ui.authorization.authorization_profile

import band.effective.office.elevator.ui.authorization.authorization_profile.store.AuthorizationProfileStore
import band.effective.office.elevator.ui.authorization.authorization_profile.store.AuthorizationProfileStoreFactory
import band.effective.office.elevator.ui.models.validator.Validator
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

class AuthorizationProfileComponent(
    componentContext: ComponentContext,
    private val storeFactory: StoreFactory,
    private val validator: Validator,
    private val output: (AuthorizationProfileComponent.Output) -> Unit
) : ComponentContext by componentContext {

    private val authorizationProfileStore =
        instanceKeeper.getStore {
            AuthorizationProfileStoreFactory(
                storeFactory = storeFactory,
                validator
            ).create()
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    val user: StateFlow<AuthorizationProfileStore.State> = authorizationProfileStore.stateFlow

    val label: Flow<AuthorizationProfileStore.Label> = authorizationProfileStore.labels

    fun onEvent(event: AuthorizationProfileStore.Intent) {
        authorizationProfileStore.accept(event)
    }

    fun onOutput(output: Output) {
        output(output)
    }

    sealed class Output {
        object OpenTGScreen : Output()

        object OpenPhoneScreen : Output()
    }
}