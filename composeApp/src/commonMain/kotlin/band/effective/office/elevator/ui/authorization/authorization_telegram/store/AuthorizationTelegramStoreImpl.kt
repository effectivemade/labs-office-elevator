package band.effective.office.elevator.ui.authorization.authorization_telegram.store

import band.effective.office.elevator.ui.models.validator.Validator
import band.effective.office.elevator.ui.models.validator.ValidatorMethods
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AuthorizationTelegramStoreFactory(
    private val storeFactory: StoreFactory,
    private val validator: Validator
) :
    KoinComponent {

    fun create(): AuthorizationTelegramStore =
        object : AuthorizationTelegramStore,
            Store<AuthorizationTelegramStore.Intent, AuthorizationTelegramStore.State, AuthorizationTelegramStore.Label> by storeFactory.create(
                name = "Authorization telegram",
                initialState = AuthorizationTelegramStore.State(),
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

    private object ReducerImpl :
        Reducer<AuthorizationTelegramStore.State, AuthorizationTelegramStore.Intent> {
        override fun AuthorizationTelegramStore.State.reduce(msg: AuthorizationTelegramStore.Intent): AuthorizationTelegramStore.State =
            when (msg) {
                AuthorizationTelegramStore.Intent.BackButtonClicked -> TODO()
                AuthorizationTelegramStore.Intent.ContinueButtonClicked -> TODO()
                is AuthorizationTelegramStore.Intent.NickChanged -> TODO()
            }
    }

    private inner class ExecutorImpl :
        CoroutineExecutor<AuthorizationTelegramStore.Intent, Action, AuthorizationTelegramStore.State, Nothing, AuthorizationTelegramStore.Label>() {
        override fun executeIntent(
            intent: AuthorizationTelegramStore.Intent,
            getState: () -> AuthorizationTelegramStore.State
        ) =
            when (intent) {
                AuthorizationTelegramStore.Intent.BackButtonClicked -> back()
                AuthorizationTelegramStore.Intent.ContinueButtonClicked -> checkTelegramNick(
                    getState().nick
                )

                is AuthorizationTelegramStore.Intent.NickChanged -> TODO()
            }

        private fun checkTelegramNick(telegramNick: String) {
            if (validator.checkTelegramNick(telegramNick))
                publish(AuthorizationTelegramStore.Label.AuthorizationTelegramSuccess)
            else
                publish(AuthorizationTelegramStore.Label.AuthorizationTelegramFailure)

        }

        private fun openMainScreen() {
            publish(AuthorizationTelegramStore.Label.OpenMainScreen)
        }

        private fun back() {
            publish(AuthorizationTelegramStore.Label.ReturnInProfileAuthorization)
        }
    }
}