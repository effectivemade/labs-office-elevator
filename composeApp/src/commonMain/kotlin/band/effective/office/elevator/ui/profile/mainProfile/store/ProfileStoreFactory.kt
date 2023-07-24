package band.effective.office.elevator.ui.profile.mainProfile.store

import band.effective.office.elevator.data.ApiResponse
import band.effective.office.elevator.domain.GoogleSignIn
import band.effective.office.elevator.domain.models.GoogleAccount
import band.effective.office.elevator.ui.models.User
import band.effective.office.elevator.ui.profile.mainProfile.store.ProfileStore.*
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
        object : ProfileStore, Store<Intent, User, Label> by storeFactory.create(
            name = "ProfileStore",
            initialState = User(null,null,null,null,null,null),
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
        data class ProfileData(val user: User) : Msg
    }

    private inner class ExecutorImpl :
        CoroutineExecutor<Intent, Action, User, Msg, Label>() {
        override fun executeIntent(intent: Intent, getState: () -> User) {
            when (intent) {
                Intent.SignOutClicked -> doSignOut()
            }
        }


        private fun doSignOut() {
            signInClient.signOut()
            publish(Label.OnSignedOut)
        }

        override fun executeAction(action: Action, getState: () -> User) {
            when (action) {
                Action.FetchUserInfo -> fetchUserInfo()
            }
        }

        private fun fetchUserInfo() {
            scope.launch {
                when (val result = signInClient.retrieveAuthorizedUser()) {
                    is ApiResponse.Error.HttpError -> {}
                    ApiResponse.Error.NetworkError -> {}
                    ApiResponse.Error.SerializationError -> {}
                    ApiResponse.Error.UnknownError -> {}
                    is ApiResponse.Success ->{}
                }
                dispatch(Msg.ProfileData(user = mokValueUser))
            }
        }
    }

    private object ReducerImpl : Reducer<User, Msg> {
        override fun User.reduce(message: Msg): User =
            when (message) {
                is Msg.ProfileData -> User(
                    imageUrl = message.user.imageUrl,
                    userName = message.user.userName,
                    telegram = message.user.telegram,
                    post = message.user.post,
                    phoneNumber = message.user.phoneNumber,
                    email = message.user.email
                )
            }
    }

}
private val mokValueUser = User("1","Ivanov Ivan", "Android-developer","67","@ivanov","employee@effective.com")
