package band.effective.office.elevator.ui.profile.mainProfile.store

import band.effective.office.elevator.domain.GoogleSignIn
import band.effective.office.elevator.ui.profile.domain.ProfileRepository
import band.effective.office.elevator.ui.profile.domain.models.User
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
    private val storeFactory: StoreFactory,
) : KoinComponent {

    private val signInClient: GoogleSignIn by inject<GoogleSignIn>()
    private val repository: ProfileRepository by inject<ProfileRepository> ()

    @OptIn(ExperimentalMviKotlinApi::class)
    fun create(): ProfileStore =
        object : ProfileStore, Store<Intent, User, Label> by storeFactory.create(
            name = "ProfileStore",
            initialState = User(imageUrl = null, userName = null,post = null,phoneNumber = null,telegram = null,email = null,id = "1"),
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
               dispatch(Msg.ProfileData(user = repository.getUser("1")))
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
                    email = message.user.email,
                    id = message.user.id
                )
            }
    }

}
