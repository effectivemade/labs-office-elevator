package band.effective.office.elevator.ui.authorization.authorization_telegram.store

import band.effective.office.elevator.ui.models.validator.Validator
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import org.koin.core.component.KoinComponent

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
                executorFactory = ::ExecutorImpl,
                reducer = ReducerImpl
            ) {
        }

    sealed interface Msg {
        data class Data(
            val nickName: String,
            val isErrorNickName: Boolean,
        ) : Msg
    }

    private sealed interface Action {

    }

    private object ReducerImpl :
        Reducer<AuthorizationTelegramStore.State, AuthorizationTelegramStoreFactory.Msg> {
        override fun AuthorizationTelegramStore.State.reduce(msg: AuthorizationTelegramStoreFactory.Msg): AuthorizationTelegramStore.State =
            when (msg) {
                is Msg.Data -> copy(
                    nick = msg.nickName,
                    isErrorNick = msg.isErrorNickName
                )
            }
    }

    private inner class ExecutorImpl :
        CoroutineExecutor<AuthorizationTelegramStore.Intent, Action, AuthorizationTelegramStore.State, AuthorizationTelegramStoreFactory.Msg, AuthorizationTelegramStore.Label>() {
        override fun executeIntent(
            intent: AuthorizationTelegramStore.Intent,
            getState: () -> AuthorizationTelegramStore.State
        ) =
            when (intent) {
                AuthorizationTelegramStore.Intent.BackButtonClicked -> back()
                AuthorizationTelegramStore.Intent.ContinueButtonClicked -> checkTelegramNick(
                    getState().nick
                )

                is AuthorizationTelegramStore.Intent.NickChanged -> dispatch(
                    Msg.Data(
                        nickName = intent.name,
                        isErrorNickName = !isErrorNickName(intent.name)
                    )
                )
            }

        private fun isErrorNickName(nickname: String) = validator.checkTelegramNick(nickname)

        private fun checkTelegramNick(telegramNick: String) {
            if (validator.checkTelegramNick(telegramNick))
                publish(AuthorizationTelegramStore.Label.AuthorizationTelegramSuccess)
            else
                publish(AuthorizationTelegramStore.Label.AuthorizationTelegramFailure)

        }

        private fun back() {
            publish(AuthorizationTelegramStore.Label.ReturnInProfileAuthorization)
        }
    }
}