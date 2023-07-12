package band.effective.office.elevator.ui.authorization.phone_authorization.store

import band.effective.office.elevator.domain.PhoneSignIn
import band.effective.office.elevator.domain.PhoneSignInResult
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineBootstrapper
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal class AuthPhoneStoreFactory(
    private val storeFactory: StoreFactory
) : KoinComponent {

    private val signInClient: PhoneSignIn by inject()

    @OptIn(ExperimentalMviKotlinApi::class)
    fun create(): AuthPhoneStore =
        object : AuthPhoneStore,
            Store<AuthPhoneStore.Intent, AuthPhoneStore.State, AuthPhoneStore.Label> by storeFactory.create(
                name = "Authorization Tab 1 Store",
                initialState = AuthPhoneStore.State(),
                bootstrapper = coroutineBootstrapper {

                },
                executorFactory = ::ExecutorImpl,
            ) {}

    private sealed interface Action {

    }


    private inner class ExecutorImpl :
        CoroutineExecutor<AuthPhoneStore.Intent, Action, AuthPhoneStore.State, Nothing, AuthPhoneStore.Label>() {
        override fun executeIntent(
            intent: AuthPhoneStore.Intent,
            getState: () -> AuthPhoneStore.State
        ) {
            when (intent) {
                AuthPhoneStore.Intent.ContinueButtonClicked -> startPhoneAuthorization()
            }
        }

        private fun startPhoneAuthorization() {
            signInClient.signIn(object : PhoneSignInResult {
                override fun onSuccess(phone: String) {
                    publish(AuthPhoneStore.Label.AuthPhoneSuccess(phone))
                }

                override fun onFailure(message: String) {
                    publish(AuthPhoneStore.Label.AuthPhoneFailure(message))
                }
            })
        }
    }
}