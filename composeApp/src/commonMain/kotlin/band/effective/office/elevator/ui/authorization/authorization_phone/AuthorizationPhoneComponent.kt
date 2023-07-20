package band.effective.office.elevator.ui.authorization.authorization_phone

import band.effective.office.elevator.ui.authorization.authorization_phone.store.AuthorizationPhoneStore
import band.effective.office.elevator.ui.authorization.authorization_phone.store.AuthorizationPhoneStoreFactory
import band.effective.office.elevator.ui.models.validator.Validator
import band.effective.office.elevator.ui.profile.editProfile.store.ProfileEditStore
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

class AuthorizationPhoneComponent(
    componentContext: ComponentContext,
    private val storeFactory: StoreFactory,
    private val validator: Validator,
    private val output: (AuthorizationPhoneComponent.Output) -> Unit
) : ComponentContext by componentContext {

    private val authorizationPhoneStore =
        instanceKeeper.getStore {
            AuthorizationPhoneStoreFactory(
                storeFactory = storeFactory,
                validator
            ).create()
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    val phone: StateFlow<AuthorizationPhoneStore.State> = authorizationPhoneStore.stateFlow

    val label: Flow<AuthorizationPhoneStore.Label> = authorizationPhoneStore.labels

    fun onEvent(event: AuthorizationPhoneStore.Intent) {
        authorizationPhoneStore.accept(event)
    }

    fun onOutput(output: Output) {
        output(output)
    }

    sealed class Output {
        object OpenProfileScreen : Output()

        object OpenGoogleScreen : Output()
    }
}
