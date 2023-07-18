package band.effective.office.elevator.ui.authorization.authorization_profile.store

import band.effective.office.elevator.ui.models.validator.Validator
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class AuthorizationProfileStoreFactory(
    private val storeFactory: StoreFactory,
    private val validator: Validator
) :
    KoinComponent {

    fun create(): AuthorizationProfileStore =
        object : AuthorizationProfileStore,
            Store<AuthorizationProfileStore.Intent, AuthorizationProfileStore.State, AuthorizationProfileStore.Label> by storeFactory.create(
                name = "Authorization profile",
                initialState = AuthorizationProfileStore.State(),
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
        Reducer<AuthorizationProfileStore.State, AuthorizationProfileStore.Intent> {
        override fun AuthorizationProfileStore.State.reduce(msg: AuthorizationProfileStore.Intent): AuthorizationProfileStore.State =
            when (msg) {
                AuthorizationProfileStore.Intent.BackButtonClicked -> TODO()
                AuthorizationProfileStore.Intent.ContinueButtonClicked -> TODO()
                is AuthorizationProfileStore.Intent.NameChanged -> TODO()
                is AuthorizationProfileStore.Intent.PostChanged -> TODO()
            }
    }

    private inner class ExecutorImpl :
        CoroutineExecutor<AuthorizationProfileStore.Intent, Action, AuthorizationProfileStore.State, Nothing, AuthorizationProfileStore.Label>() {
        override fun executeIntent(
            intent: AuthorizationProfileStore.Intent,
            getState: () -> AuthorizationProfileStore.State
        ) =
            when (intent) {
                AuthorizationProfileStore.Intent.BackButtonClicked -> back()
                AuthorizationProfileStore.Intent.ContinueButtonClicked -> checkUserdata(
                    getState().name,
                    getState().post
                )

                is AuthorizationProfileStore.Intent.PostChanged -> TODO()
                is AuthorizationProfileStore.Intent.NameChanged -> TODO()
            }

        private fun checkUserdata(name: String, post: String) {
            if (validator.checkName(name) && validator.checkPost(post)) {
                publish(AuthorizationProfileStore.Label.AuthorizationProfileSuccess)
            } else {
                publish(AuthorizationProfileStore.Label.AuthorizationProfileFailure)
            }
        }

        private fun openTGAuthorization() {
            publish(AuthorizationProfileStore.Label.OpenTGAuthorization)
        }

        private fun back() {
            publish(AuthorizationProfileStore.Label.ReturnInPhoneAuthorization)
        }
    }
}