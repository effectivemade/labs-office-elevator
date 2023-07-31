package band.effective.office.elevator.ui.profile.editProfile.store

import band.effective.office.elevator.domain.models.User
import band.effective.office.elevator.domain.usecase.GetUserByIdUseCase
import band.effective.office.elevator.domain.usecase.UpdateUserUseCase
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import band.effective.office.elevator.ui.profile.editProfile.store.ProfileEditStore.*
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineBootstrapper
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal class ProfileEditStoreFactory(
    private val storeFactory: StoreFactory,
    private  val user: String,
) : KoinComponent {

    private val getUserByIdUseCase:GetUserByIdUseCase by inject()
    private val updateUserUseCase:UpdateUserUseCase by inject()

    @OptIn(ExperimentalMviKotlinApi::class)
    fun create(): ProfileEditStore =
        object : ProfileEditStore,
            Store<Intent, User, Label>
            by storeFactory.create(
                name = "ProfileEditStore",
                initialState = User("","","","","","",""),
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
                is Intent.SaveChangeClicked -> doSaveChange(getState(),intent)
            }
        }
        private fun doSaveChange(user: User, intent: Intent.SaveChangeClicked) {
            scope.launch {
                val uptUser = User(id = user.id, imageUrl = user.imageUrl, userName = intent.userName, post = intent.post, phoneNumber = intent.phoneNumber, telegram = intent.telegram,email = user.email)
                dispatch(Msg.ProfileData(user = uptUser))
                updateUserUseCase.execute(uptUser)
                publish(Label.SavedChange)
            }

        }
        override fun executeAction(action: Action, getState: () -> User) {
            when (action) {
                Action.FetchUserInfo -> fetchUserInfo()
            }
        }

        private fun fetchUserInfo() {
            scope.launch {
                getUserByIdUseCase.execute(user).collectLatest {
                        user -> dispatch(Msg.ProfileData(user = user))
                }
            }
        }

        private fun doReturnProfile() {
            publish(Label.ReturnedInProfile)
        }
    }

    private object ReducerImpl : Reducer<User, Msg> {
        override fun User.reduce(message: Msg): User =
            when (message) {
                is Msg.ProfileData ->
                    copy(id = message.user.id,
                    userName = message.user.userName,
                    telegram = message.user.telegram,
                    post = message.user.post,
                    phoneNumber = message.user.phoneNumber,
                    imageUrl = message.user.imageUrl,
                    email = message.user.email)
            }
    }
}