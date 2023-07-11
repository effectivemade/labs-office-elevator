package band.effective.office.elevator.ui.profile.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
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
            Store<ProfileEditStore.Intent, ProfileEditStore.User, ProfileEditStore.Label>
            by storeFactory.create(
                name = "ProfileEditStore",
                initialState = ProfileEditStore.User(
                    username = null,
                    telegram = null,
                    post = null,
                    phoneNumber = null
                ),
                bootstrapper = coroutineBootstrapper {
                    dispatch(ProfileEditStoreFactory.Action.FetchUserInfo)
                },
                executorFactory = ::ExecutorImpl,
                reducer = ProfileEditStoreFactory.ReducerImpl
            ) {}

    private sealed interface Action {
        object FetchUserInfo : Action
    }

    private sealed interface Msg {
        data class ProfileData(val user: ProfileEditStore.User) : Msg
    }

    private inner class ExecutorImpl :
        CoroutineExecutor<ProfileEditStore.Intent,Action, ProfileEditStore.User,Msg, ProfileEditStore.Label>() {
        override fun executeIntent(
            intent: ProfileEditStore.Intent,
            getState: () -> ProfileEditStore.User
        ) {
            when (intent) {
                ProfileEditStore.Intent.BackInProfileClicked -> doReturnProfile()
                ProfileEditStore.Intent.SaveChangeClicked -> doSaveChange()
                else -> {}
            }
        }

        override fun executeAction(action: Action, getState: () -> ProfileEditStore.User) {
            when (action) {
                Action.FetchUserInfo -> fetchUserInfo()
            }
        }

        private fun fetchUserInfo() {
            TODO("Not yet implemented")
        }

        private fun doSaveChange() {
            TODO("Not yet implemented")
        }

        private fun doReturnProfile() {
            TODO("Not yet implemented")
        }
    }

    private object ReducerImpl : Reducer<ProfileEditStore.User, Msg> {
        override fun ProfileEditStore.User.reduce(message: Msg): ProfileEditStore.User =
            when (message) {
                is Msg.ProfileData -> ProfileEditStore.User(
                    username = null,
                    telegram = null,
                    post = null,
                    phoneNumber = null,
                )
            }
    }
}