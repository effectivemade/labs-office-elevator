package band.effective.office.elevator.ui.authorization.profile_authorization.store

import band.effective.office.elevator.domain.profile_domain.ProfileSignIn
import band.effective.office.elevator.domain.profile_domain.ProfileSignInResult
import band.effective.office.elevator.ui.models.User
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineBootstrapper
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal class AuthProfileStoreFactory(
    private val storeFactory: StoreFactory
) : KoinComponent {

    private val signInClient: ProfileSignIn by inject()

    @OptIn(ExperimentalMviKotlinApi::class)
    fun create(): AuthProfileStore =
        object : AuthProfileStore,
            Store<AuthProfileStore.Intent, AuthProfileStore.State, AuthProfileStore.Label> by storeFactory.create(
                name = "Authorization Tab 2 Store",
                initialState = AuthProfileStore.State(user = User ("name", "post", "986875756", "@tg_nick"), currentTabIndex = 1),
                bootstrapper = coroutineBootstrapper {

                },
                executorFactory = ::ExecutorImpl,
            ) {}

    private sealed interface Action {

    }


    private inner class ExecutorImpl :
        CoroutineExecutor<AuthProfileStore.Intent, Action, AuthProfileStore.State, Nothing, AuthProfileStore.Label>() {
        override fun executeIntent(
            intent: AuthProfileStore.Intent,
            getState: () -> AuthProfileStore.State
        ) {
            when (intent) {
                AuthProfileStore.Intent.ContinueButtonClicked -> startProfileAuthorization()
            }
        }

        private fun startProfileAuthorization() {
            signInClient.signIn(object : ProfileSignInResult {
                override fun onSuccess(name: String, function: String) {
                    publish(AuthProfileStore.Label.AuthProfileSuccess)
                }

                override fun onFailure(message: String) {
                    publish(AuthProfileStore.Label.AuthProfileFailure(message))
                }
            })
        }
    }
}
