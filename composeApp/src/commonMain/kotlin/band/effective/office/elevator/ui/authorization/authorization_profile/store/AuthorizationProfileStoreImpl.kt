package band.effective.office.elevator.ui.authorization.authorization_profile.store

import band.effective.office.elevator.domain.models.UserData
import band.effective.office.elevator.ui.models.validator.Validator
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineBootstrapper
import kotlinx.coroutines.launch

class AuthorizationProfileStoreFactory(
    private val storeFactory: StoreFactory,
    private val validator: Validator,
    private val userData: UserData
) {

    @OptIn(ExperimentalMviKotlinApi::class)
    fun create(): AuthorizationProfileStore =
        object : AuthorizationProfileStore,
            Store<AuthorizationProfileStore.Intent, AuthorizationProfileStore.State, AuthorizationProfileStore.Label> by storeFactory.create(
                name = "Authorization profile",
                initialState = AuthorizationProfileStore.State(),
                bootstrapper = coroutineBootstrapper {
                    launch {
                        dispatch(AuthorizationProfileStoreFactory.Action.InitUser)
                    }
                },
                executorFactory = ::ExecutorImpl,
                reducer = ReducerImpl
            ) {
        }

    private sealed interface Msg {
        data class NameData(
            var name: String,
            var isNameError: Boolean
        ) : Msg

        data class PostData(
            var post: String,
            var isPostError: Boolean
        ) : Msg
    }

    private sealed interface Action {
        object InitUser : Action
    }

    private object ReducerImpl :
        Reducer<AuthorizationProfileStore.State, AuthorizationProfileStoreFactory.Msg> {
        override fun AuthorizationProfileStore.State.reduce(msg: AuthorizationProfileStoreFactory.Msg): AuthorizationProfileStore.State =
            when (msg) {
                is Msg.NameData -> copy(
                    name = msg.name,
                    isErrorName = msg.isNameError
                )

                is Msg.PostData -> copy(
                    post = msg.post,
                    isErrorPost = msg.isPostError
                )
            }
    }

    private inner class ExecutorImpl :
        CoroutineExecutor<AuthorizationProfileStore.Intent, Action, AuthorizationProfileStore.State, AuthorizationProfileStoreFactory.Msg, AuthorizationProfileStore.Label>() {
        override fun executeIntent(
            intent: AuthorizationProfileStore.Intent,
            getState: () -> AuthorizationProfileStore.State
        ) =
            when (intent) {
                AuthorizationProfileStore.Intent.BackButtonClicked -> back()
                AuthorizationProfileStore.Intent.ContinueButtonClicked -> checkUserdata(
                    getState().name,
                    getState().post
                )

                is AuthorizationProfileStore.Intent.PostChanged -> with(intent.post) {
                    dispatch(
                        AuthorizationProfileStoreFactory.Msg.PostData(
                            post = this,
                            isPostError = validator.checkPost(this)
                        )
                    )
                }

                is AuthorizationProfileStore.Intent.NameChanged ->
                    dispatch(
                        AuthorizationProfileStoreFactory.Msg.NameData(
                            name = intent.name,
                            isNameError = validator.checkName(intent.name)
                        )
                    )
            }

        override fun executeAction(
            action: Action,
            getState: () -> AuthorizationProfileStore.State
        ) {
            when (action) {
                is AuthorizationProfileStoreFactory.Action.InitUser -> {

                    dispatch(
                        AuthorizationProfileStoreFactory.Msg.PostData(
                            post = userData.post!!,
                            isPostError = userData.post == null
                        )
                    )

                    dispatch(
                        AuthorizationProfileStoreFactory.Msg.NameData(
                            name = userData.name,
                            isNameError = false
                        )
                    )
                }
            }
        }

        private fun checkUserdata(name: String, post: String) {
            if (!validator.checkName(name) && !validator.checkPost(post)) {
                userData.post = post
                userData.name = name
                publish(AuthorizationProfileStore.Label.AuthorizationProfileSuccess(userData))
            } else {
                publish(AuthorizationProfileStore.Label.AuthorizationProfileFailure)
            }
        }

        private fun back() {
            publish(AuthorizationProfileStore.Label.ReturnInPhoneAuthorization(userData))
        }
    }
}