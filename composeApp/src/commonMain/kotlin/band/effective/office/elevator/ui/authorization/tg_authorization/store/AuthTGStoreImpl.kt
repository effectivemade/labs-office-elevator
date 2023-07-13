package band.effective.office.elevator.ui.authorization.tg_authorization.store

import band.effective.office.elevator.domain.profile_domain.ProfileSignIn
import band.effective.office.elevator.domain.profile_domain.ProfileSignInResult
import band.effective.office.elevator.domain.tg_domain.TGSignIn
import band.effective.office.elevator.domain.tg_domain.TGSignInResult
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

    private val signInClient: TGSignIn by inject()

    @OptIn(ExperimentalMviKotlinApi::class)
    fun create(): AuthTGStore =
        object : AuthTGStore,
            Store<AuthTGStore.Intent, AuthTGStore.State, AuthTGStore.Label> by storeFactory.create(
                name = "Authorization Tab 3 Store",
                initialState = AuthTGStore.State(
                    user = User(
                        "name",
                        "post",
                        "986875756",
                        "@tg_nick"
                    ), currentTabIndex = 0
                ),
                bootstrapper = coroutineBootstrapper {

                },
                executorFactory = ::ExecutorImpl,
            ) {}

    private sealed interface Action {

    }


    private inner class ExecutorImpl :
        CoroutineExecutor<AuthTGStore.Intent, Action, AuthTGStore.State, Nothing, AuthTGStore.Label>() {
        override fun executeIntent(
            intent: AuthTGStore.Intent,
            getState: () -> AuthTGStore.State
        ) {
            when (intent) {
                AuthTGStore.Intent.ContinueButtonClicked -> startProfileAuthorization()
            }
        }

        private fun startProfileAuthorization() {
            signInClient.signIn(object : TGSignInResult {
                override fun onSuccess(nick: String) {
                    publish(AuthTGStore.Label.AuthTGSuccess)
                }

                override fun onFailure(message: String) {
                    publish(AuthTGStore.Label.AuthTGFailure(message))
                }
            })
        }
    }
}
