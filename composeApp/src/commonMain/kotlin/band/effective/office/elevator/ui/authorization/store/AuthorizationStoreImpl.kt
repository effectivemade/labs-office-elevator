package band.effective.office.elevator.ui.authorization.store

import band.effective.office.elevator.domain.GoogleSignIn
import band.effective.office.elevator.domain.SignInResultCallback
import band.effective.office.elevator.ui.authorization.store.AuthorizationStore.Intent
import band.effective.office.elevator.ui.authorization.store.AuthorizationStore.Label
import band.effective.office.elevator.ui.authorization.store.AuthorizationStore.State
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineBootstrapper
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal class AuthorizationStoreFactory(
    private val storeFactory: StoreFactory
) : KoinComponent {

    private val signInClient: GoogleSignIn by inject<GoogleSignIn>()

    @OptIn(ExperimentalMviKotlinApi::class)
    fun create(): AuthorizationStore =
        object : AuthorizationStore, Store<Intent, State, Label> by storeFactory.create(
            name = "AuthorizationStore",
            initialState = State(),
            bootstrapper = coroutineBootstrapper {
                launch { dispatch(Action.CheckUserAlreadySigned) }
            },
            executorFactory = ::ExecutorImpl,
        ) {}

    private sealed interface Action {
        object CheckUserAlreadySigned : Action
    }


    private inner class ExecutorImpl : CoroutineExecutor<Intent, Action, State, Nothing, Label>() {
        override fun executeIntent(intent: Intent, getState: () -> State) {
            when (intent) {
                Intent.SignInButtonClicked -> startAuthorization()
            }
        }

        private fun startAuthorization() {
            signInClient.signIn(object : SignInResultCallback {
                override fun onSuccess() {
                    publish(Label.AuthorizationSuccess)
                }

                override fun onFailure(message: String) {
                    publish(Label.AuthorizationFailure(message))
                }
            })
        }
    }
}
