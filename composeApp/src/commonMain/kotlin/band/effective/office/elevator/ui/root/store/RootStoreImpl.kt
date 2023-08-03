package band.effective.office.elevator.ui.root.store

import band.effective.office.elevator.data.ApiResponse
import band.effective.office.elevator.data.database.DBSource
import band.effective.office.elevator.domain.GoogleSignIn
import band.effective.office.elevator.ui.root.store.RootStore.Label
import band.effective.office.elevator.ui.root.store.RootStore.State
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineBootstrapper
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal class RootStoreImplFactory(
    private val storeFactory: StoreFactory
) : KoinComponent {

    private val signInClient: GoogleSignIn by inject<GoogleSignIn>()

    @OptIn(ExperimentalMviKotlinApi::class)
    fun create(): RootStore =
        object : RootStore, Store<Nothing, State, Label> by storeFactory.create(
            name = "RootStoreImplStore",
            initialState = State(),
            bootstrapper = coroutineBootstrapper {
                launch { dispatch(Action.CheckUserAlreadySigned) }
            },
            executorFactory = ::ExecutorImpl
        ) {}

    private sealed interface Action {
        object CheckUserAlreadySigned : Action
    }

    private inner class ExecutorImpl : CoroutineExecutor<Nothing, Action, State, Nothing, Label>() {
        override fun executeAction(action: Action, getState: () -> State) {
            when (action) {
                Action.CheckUserAlreadySigned -> checkUserAlreadySigned()
            }
        }

        private fun checkUserAlreadySigned() {
            scope.launch {
                when (signInClient.retrieveAuthorizedUser()) {
                    is ApiResponse.Error.HttpError -> TODO()
                    ApiResponse.Error.NetworkError -> TODO()
                    ApiResponse.Error.SerializationError -> TODO()
                    ApiResponse.Error.UnknownError -> {
                        publish(Label.UserNotSigned)
                    }
                    is ApiResponse.Success -> publish(Label.UserAlreadySigned)
                }
            }
        }
    }
}
