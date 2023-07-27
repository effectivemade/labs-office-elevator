package band.effective.office.elevator.ui.authorization.authorization_phone.store

import band.effective.office.elevator.data.ApiResponse
import band.effective.office.elevator.domain.GoogleSignIn
import band.effective.office.elevator.domain.models.UserData
import band.effective.office.elevator.domain.repository.UserProfileRepository
import band.effective.office.elevator.domain.usecase.phone_authorization.GetUserUseCase
import band.effective.office.elevator.ui.authorization.authorization_phone.store.AuthorizationPhoneStore.*
import band.effective.office.elevator.ui.models.validator.Validator
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineBootstrapper
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal class AuthorizationPhoneStoreFactory(
    private val storeFactory: StoreFactory,
    private val validator: Validator
) : KoinComponent {

    private val userProfileRep: UserProfileRepository by inject()
    private val getUserUseCase: GetUserUseCase = GetUserUseCase(userProfileRep)
    private val signInClient: GoogleSignIn by inject()

    @OptIn(ExperimentalMviKotlinApi::class)
    fun create(): AuthorizationPhoneStore =
        object : AuthorizationPhoneStore,
            Store<Intent, State, Label> by storeFactory.create(
                name = "Authorization phone",
                initialState = State(),
                bootstrapper = coroutineBootstrapper {
                    dispatch(AuthorizationPhoneStoreFactory.Action.PushToken)
                },
                executorFactory = ::ExecutorImpl,
                reducer = ReducerImpl
            ) {
        }

    private sealed interface Action {
        object PushToken : Action
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

        override fun executeAction(action: Action, getState: () -> State) {
            when (action) {
                is Action.PushToken -> {
                    pushToken()
                }
            }
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

        private fun pushToken() {
            scope.launch {
                when (val result = signInClient.retrieveAuthorizedUser()) {
                    is ApiResponse.Error.HttpError -> {}
                    ApiResponse.Error.NetworkError -> {}
                    ApiResponse.Error.SerializationError -> {}
                    ApiResponse.Error.UnknownError -> {}
                    is ApiResponse.Success -> {
                        val userData: UserData =
                            getUserUseCase.execute(result.body.idToken!!)
                        if (userData.phoneNumber.isNotEmpty())
                            dispatch(AuthorizationPhoneStoreFactory.Msg.Data(phoneNumber = userData.phoneNumber))
                    }
                }
            }
        }
    }
}