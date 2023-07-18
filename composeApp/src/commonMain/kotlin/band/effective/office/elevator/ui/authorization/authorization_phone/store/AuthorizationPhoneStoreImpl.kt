package band.effective.office.elevator.ui.authorization.authorization_phone.store

import band.effective.office.elevator.ui.authorization.authorization_phone.store.AuthorizationPhoneStore.*
import band.effective.office.elevator.ui.models.validator.Validator
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal class AuthorizationPhoneStoreFactory(
    private val storeFactory: StoreFactory,
    private val validator: Validator
) :
    KoinComponent {

    fun create(): AuthorizationPhoneStore =
        object : AuthorizationPhoneStore,
            Store<Intent, State, Label> by storeFactory.create(
                name = "Authorization phone",
                initialState = State(),
                bootstrapper = BootstrapperImpl(),
                executorFactory = ::ExecutorImpl,
                reducer = ReducerImpl
            ) {
        }

    private class BootstrapperImpl : CoroutineBootstrapper<Action>() {
        override fun invoke() {
            scope.launch {

            }
        }
    }

    private sealed interface Action {

    }

    private object ReducerImpl : Reducer<State, Intent> {
        override fun State.reduce(msg: Intent): State =
            when (msg) {
                Intent.BackButtonClicked -> TODO()
                Intent.ContinueButtonClicked -> TODO()
                is Intent.PhoneNumberChanged -> TODO()
            }
    }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Action, State, Nothing, Label>() {
        override fun executeIntent(intent: Intent, getState: () -> State) =
            when (intent) {
                Intent.BackButtonClicked -> back()
                Intent.ContinueButtonClicked -> checkPhoneNumber(getState().phoneNumber)
                is Intent.PhoneNumberChanged -> TODO()
            }

        private fun checkPhoneNumber(phoneNumber: String) {
            if (validator.checkPhone(phoneNumber))
                publish(AuthorizationPhoneStore.Label.AuthorizationPhoneSuccess)
            else
                publish(AuthorizationPhoneStore.Label.AuthorizationPhoneFailure)
        }

        private fun back() {
            publish(AuthorizationPhoneStore.Label.ReturnInGoogleAuthorization)
        }
    }
}