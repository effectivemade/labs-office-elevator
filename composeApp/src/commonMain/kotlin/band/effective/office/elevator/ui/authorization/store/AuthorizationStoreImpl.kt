package band.effective.office.elevator.ui.authorization.store

import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor

class AuthorizationStoreFactory(
    private val storeFactory: StoreFactory
) {

    fun create(): AuthorizationStore =
        object : AuthorizationStore,
            Store<AuthorizationStore.Intent, AuthorizationStore.State, AuthorizationStore.Label> by storeFactory.create(
                name = "Authorization store",
                initialState = AuthorizationStore.State(),
                executorFactory = ::ExecutorImpl,
            ) {
        }


    private inner class ExecutorImpl :
        CoroutineExecutor<AuthorizationStore.Intent, Nothing, AuthorizationStore.State, Nothing, AuthorizationStore.Label>() {
        override fun executeIntent(
            intent: AuthorizationStore.Intent,
            getState: () -> AuthorizationStore.State
        ) {
            when (intent) {
                AuthorizationStore.Intent.ContinueButtonClicked -> TODO()
            }
        }
    }
}