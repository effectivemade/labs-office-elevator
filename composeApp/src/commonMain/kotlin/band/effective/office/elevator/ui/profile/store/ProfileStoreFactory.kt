package band.effective.office.elevator.ui.profile.store

import band.effective.office.elevator.data.ApiResponse
import band.effective.office.elevator.domain.GoogleSignIn
import band.effective.office.elevator.domain.models.GoogleAccount
import band.effective.office.elevator.ui.profile.store.ProfileStore.Intent
import band.effective.office.elevator.ui.profile.store.ProfileStore.State
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineBootstrapper
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal class ProfileStoreFactory(
    private val storeFactory: StoreFactory
) : KoinComponent {

    private val signInClient: GoogleSignIn by inject<GoogleSignIn>()

    @OptIn(ExperimentalMviKotlinApi::class)
    fun create(): ProfileStore =
        object : ProfileStore, Store<Intent, State, ProfileStore.Label> by storeFactory.create(
            name = "ProfileStore",
            initialState = State(imageUrl = null, username = null, email = null),
            bootstrapper = coroutineBootstrapper {
                dispatch(Action.FetchUserInfo)
            },
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed interface Action {
        object FetchUserInfo : Action
    }

    private sealed interface Msg {
        data class ProfileData(val user: GoogleAccount) : Msg
    }

    private inner class ExecutorImpl :
        CoroutineExecutor<Intent, Action, State, Msg, ProfileStore.Label>() {
        override fun executeIntent(intent: Intent, getState: () -> State) {
            when (intent) {
                Intent.SignOutClicked -> doSignOut()
            }
        }

        private fun doSignOut() {
            signInClient.signOut()
            publish(ProfileStore.Label.OnSignedOut)
        }

        override fun executeAction(action: Action, getState: () -> State) {
            when (action) {
                Action.FetchUserInfo -> fetchUserInfo()
            }
        }

        private fun fetchUserInfo() {
            scope.launch {
                when (val result = signInClient.retrieveAuthorizedUser()) {
                    is ApiResponse.Error.HttpError -> TODO()
                    ApiResponse.Error.NetworkError -> TODO()
                    ApiResponse.Error.SerializationError -> TODO()
                    ApiResponse.Error.UnknownError -> TODO()
                    is ApiResponse.Success -> dispatch(Msg.ProfileData(user = result.body))
                }
            }
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(message: Msg): State =
            when (message) {
                is Msg.ProfileData -> State(
                    imageUrl = message.user.photoUrl,
                    username = message.user.name,
                    email = message.user.email
                )
            }
    }
}
