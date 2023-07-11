package band.effective.office.elevator.ui.authorization.screen_2.store

import band.effective.office.elevator.domain.GoogleSignIn
import band.effective.office.elevator.domain.SignInResultCallback
import band.effective.office.elevator.ui.authorization.store.AuthorizationStore
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineBootstrapper
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal class AuthTab1StoreFactory(
    private val storeFactory: StoreFactory
) : KoinComponent {

    private val signInClient: GoogleSignIn by inject<GoogleSignIn>()

    @OptIn(ExperimentalMviKotlinApi::class)
    fun create(): AuthTab1Store =
        object : AuthTab1Store, Store<AuthTab1Store.Intent, AuthTab1Store.State, AuthTab1Store.Label> by storeFactory.create(
            name = "Authorization Tab 1 Store",
            initialState = AuthorizationStore.State(),
            bootstrapper = coroutineBootstrapper {
                launch {  }
            },
            executorFactory = ::ExecutorImpl,
        ) {}

    private sealed interface Action {

    }


    private inner class ExecutorImpl : CoroutineExecutor<AuthorizationStore.Intent, Action, AuthorizationStore.State, Nothing, AuthorizationStore.Label>() {
        override fun executeIntent(intent: AuthorizationStore.Intent, getState: () -> AuthorizationStore.State) {
            when (intent) {
                AuthorizationStore.Intent.SignInButtonClicked -> startAuthorization()
            }
        }

        private fun startAuthorization() {
            signInClient.signIn(object : SignInResultCallback {
                override fun onSuccess() {
                    publish(AuthorizationStore.Label.AuthorizationSuccess)
                }

                override fun onFailure(message: String) {
                    publish(AuthorizationStore.Label.AuthorizationFailure(message))
                }
            })
        }
    }
}