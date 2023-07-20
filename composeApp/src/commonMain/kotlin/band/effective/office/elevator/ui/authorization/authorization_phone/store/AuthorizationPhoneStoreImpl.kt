package band.effective.office.elevator.ui.authorization.authorization_phone.store

import band.effective.office.elevator.ui.authorization.authorization_phone.store.AuthorizationPhoneStore.*
import band.effective.office.elevator.ui.models.validator.Validator
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import org.koin.core.component.KoinComponent

internal class AuthorizationPhoneStoreFactory(
    private val storeFactory: StoreFactory,
    private val validator: Validator
) :
    KoinComponent {

    fun create(): AuthorizationPhoneStore =
        object : AuthorizationPhoneStore,
            Store<Intent, State, Label> by storeFactory.create(
                name = "Authorization phone",
                initialState = State(),
                executorFactory = ::ExecutorImpl,
                reducer = ReducerImpl
            ) {
        }

    private sealed interface Action {

    }

    sealed interface Msg {
        data class Error(
            val error: Boolean
        ) : Msg

        data class Data(
            val phoneNumber: String,
        ) : Msg
    }

    private object ReducerImpl : Reducer<AuthorizationPhoneStore.State, Msg> {
        override fun State.reduce(msg: Msg): AuthorizationPhoneStore.State =
            when (msg) {
                is Msg.Data -> copy(
                    phoneNumber = msg.phoneNumber,
                )

                is Msg.Error -> copy(
                    isErrorPhoneNumber = msg.error
                )
            }
    }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Action, State, Msg, Label>() {

        override fun executeIntent(intent: Intent, getState: () -> State) =
            when (intent) {
                Intent.BackButtonClicked -> back()
                Intent.ContinueButtonClicked -> checkPhoneNumber(getState().phoneNumber)
                is Intent.PhoneNumberChanged ->
                    dispatch(
                        Msg.Data(
                            phoneNumber = intent.phoneNumber
                        )
                    )

            }

        private fun checkPhoneNumber(phoneNumber: String) {
            if (validator.checkPhone(phoneNumber)) {
                publish(AuthorizationPhoneStore.Label.AuthorizationPhoneSuccess)
                dispatch(
                    AuthorizationPhoneStoreFactory.Msg.Error(
                        error = false
                    )
                )
            } else {
                dispatch(
                    AuthorizationPhoneStoreFactory.Msg.Error(
                        error = true
                    )
                )
                publish(AuthorizationPhoneStore.Label.AuthorizationPhoneFailure)
            }
        }

        private fun back() {
            publish(AuthorizationPhoneStore.Label.ReturnInGoogleAuthorization)
        }
    }
}