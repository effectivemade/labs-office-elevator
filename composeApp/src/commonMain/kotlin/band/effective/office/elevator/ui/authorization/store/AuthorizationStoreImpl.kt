package band.effective.office.elevator.ui.authorization.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class AuthorizationStoreFactory(
    private val storeFactory: StoreFactory
) : KoinComponent {

    fun create(): AuthorizationStore =
        object : AuthorizationStore,
            Store<AuthorizationStore.Intent, AuthorizationStore.State, AuthorizationStore.Label> by storeFactory.create(
                name = "Authorization store",
                initialState = AuthorizationStore.State(),
                executorFactory = ::ExecutorImpl,
                reducer = ReducerImpl
            ) {
        }

    private inner class ExecutorImpl :
        CoroutineExecutor<AuthorizationStore.Intent, Nothing, AuthorizationStore.State, Msg, AuthorizationStore.Label>() {
        override fun executeIntent(
            intent: AuthorizationStore.Intent,
            getState: () -> AuthorizationStore.State
        ) {
            scope.launch {
                when (intent) {
                    AuthorizationStore.Intent.ContinueButtonClicked -> {
                        TODO()
                    }
                    is AuthorizationStore.Intent.ChangeEmail -> dispatch(Msg.ChangeEmail(intent.email))
                    is AuthorizationStore.Intent.ChangeName -> dispatch(Msg.ChangeName(intent.name))
                    is AuthorizationStore.Intent.ChangePhoneNumber -> dispatch(Msg.ChangePhoneNumber(intent.phoneNumber))
                    is AuthorizationStore.Intent.ChangePost -> dispatch(Msg.ChangePost(intent.post))
                    is AuthorizationStore.Intent.ChangeTelegram -> dispatch(Msg.ChangeTelegram(intent.telegram))
                }
            }
        }
    }

    private object ReducerImpl: Reducer<AuthorizationStore.State, Msg > {
        override fun AuthorizationStore.State.reduce(msg: Msg): AuthorizationStore.State =
            when(msg) {
                is Msg.ChangeEmail -> copy(userData = userData.copy(email = msg.email))
                is Msg.ChangeName -> copy(userData = userData.copy(userName = msg.name))
                is Msg.ChangePhoneNumber -> copy(userData = userData.copy(phoneNumber = msg.phoneNumber))
                is Msg.ChangePost -> copy(userData = userData.copy(post = msg.post))
                is Msg.ChangeTelegram -> copy(userData = userData.copy(telegram = msg.telegram))
            }
    }

    private sealed interface Msg {
        data class ChangeEmail(val email: String) : Msg
        data class ChangePhoneNumber(val phoneNumber: String) : Msg
        data class ChangeName(val name: String) : Msg
        data class ChangePost(val post: String) : Msg
        data class ChangeTelegram(val telegram: String) : Msg
    }
}