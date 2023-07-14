package band.effective.office.elevator.ui.profile.editProfile.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import band.effective.office.elevator.ui.profile.editProfile.store.ProfileEditStore.*
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineBootstrapper
import org.koin.core.component.KoinComponent

internal class ProfileEditStoreFactory(
    private val storeFactory: StoreFactory
) : KoinComponent {
    @OptIn(ExperimentalMviKotlinApi::class)
    fun create(): ProfileEditStore =
        object : ProfileEditStore,
            Store<Intent, User, Label>
            by storeFactory.create(
                name = "ProfileEditStore",
                initialState = User(
                    userName = null,
                    telegram = null,
                    post = null,
                    phoneNumber = null
                ),
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
        override fun executeIntent(
            intent: Intent,
            getState: () -> User
        ) {
            when (intent) {
                Intent.BackInProfileClicked -> doReturnProfile()
                Intent.SaveChangeClicked -> doSaveChange()
            }
        }
        private fun doSaveChange() {
            publish(Label.SavedChange)
        }
        override fun executeAction(action: Action, getState: () -> User) {
            when (action) {
                Action.FetchUserInfo -> fetchUserInfo()
            }
        }

        private fun fetchUserInfo() {

        }



        private fun doReturnProfile() {
            publish(Label.ReturnedInProfile)
        }
    }

    private object ReducerImpl : Reducer<User, Msg> {
        override fun User.reduce(message: Msg): User =
            when (message) {
                is Msg.ProfileData -> User(
                    userName = null,
                    telegram = null,
                    post = null,
                    phoneNumber = null,
                )
            }
    }
}