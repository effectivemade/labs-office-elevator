package band.effective.office.elevator.ui.authorization.authorization_google.store

import band.effective.office.elevator.data.ApiResponse
import band.effective.office.elevator.domain.GoogleSignIn
import band.effective.office.elevator.domain.SignInResultCallback
import band.effective.office.elevator.domain.entity.AuthorizationEntity
import band.effective.office.elevator.domain.models.UserData
import band.effective.office.elevator.domain.usecase.GetUserUseCase
import band.effective.office.elevator.ui.authorization.authorization_google.store.AuthorizationGoogleStore.Intent
import band.effective.office.elevator.ui.authorization.authorization_google.store.AuthorizationGoogleStore.Label
import band.effective.office.elevator.ui.authorization.authorization_google.store.AuthorizationGoogleStore.State
import band.effective.office.elevator.ui.authorization.authorization_phone.store.AuthorizationPhoneStoreFactory
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineBootstrapper
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal class AuthorizationGoogleStoreFactory(
    private val storeFactory: StoreFactory
) : KoinComponent {

    private val signInClient: GoogleSignIn by inject()
    private val authorizationEntity: AuthorizationEntity by inject()

    @OptIn(ExperimentalMviKotlinApi::class)
    fun create(): AuthorizationGoogleStore =
        object : AuthorizationGoogleStore, Store<Intent, State, Label> by storeFactory.create(
            name = "AuthorizationStore",
            initialState = State(),
            bootstrapper = coroutineBootstrapper {
                launch { dispatch(Action.CheckUserAlreadySigned) }
            },
            executorFactory = ::ExecutorImpl,
        ) {}

    private sealed interface Action {
        object CheckUserAlreadySigned : Action
    }


    private inner class ExecutorImpl :
        CoroutineExecutor<Intent, Action, State, AuthorizationPhoneStoreFactory.Msg, Label>() {
        override fun executeIntent(intent: Intent, getState: () -> State) {
            when (intent) {
                Intent.SignInButtonClicked -> startAuthorization()
            }
        }

        private fun startAuthorization() {
            signInClient.signIn(object : SignInResultCallback {
                override fun onSuccess() {
                    scope.launch {
                        when (val result = signInClient.retrieveAuthorizedUser()) {
                            is ApiResponse.Error.HttpError -> {}
                            ApiResponse.Error.NetworkError -> {}
                            ApiResponse.Error.SerializationError -> {}
                            ApiResponse.Error.UnknownError -> {}
                            is ApiResponse.Success -> {
                                val userData: UserData =
                                    authorizationEntity.get(result.body.idToken!!)
                                publish(Label.AuthorizationSuccess(userData))
                            }
                        }
                    }
                }

                override fun onFailure(message: String) {
                    publish(Label.AuthorizationFailure(message))
                }
            })
        }
    }
}
