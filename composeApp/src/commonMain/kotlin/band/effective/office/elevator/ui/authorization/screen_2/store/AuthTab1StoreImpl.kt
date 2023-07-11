package band.effective.office.elevator.ui.authorization.screen_2.store

import band.effective.office.elevator.domain.GoogleSignIn
import band.effective.office.elevator.domain.SignInResultCallback
import band.effective.office.elevator.ui.authorization.store.AuthorizationStore
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineBootstrapper
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
            initialState = AuthTab1Store.State(),
            bootstrapper = coroutineBootstrapper {

            },
            executorFactory = ::ExecutorImpl,
        ) {}

    private sealed interface Action {
        object Continue : Action
    }


    private inner class ExecutorImpl : CoroutineExecutor<AuthTab1Store.Intent, Action, AuthTab1Store.State, Nothing, AuthTab1Store.Label>() {
        override fun executeIntent(intent: AuthTab1Store.Intent, getState: () -> AuthTab1Store.State) {
//            when (intent) {
//                AuthTab1Store.Intent.ContinueButtonClicked ->
//            }
        }
    }
}