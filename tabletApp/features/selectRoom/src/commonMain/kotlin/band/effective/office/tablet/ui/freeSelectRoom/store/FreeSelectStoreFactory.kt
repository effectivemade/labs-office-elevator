package band.effective.office.tablet.ui.freeSelectRoom.store

import band.effective.office.tablet.domain.CurrentEventController
import band.effective.office.tablet.domain.model.Either
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineBootstrapper
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class FreeSelectStoreFactory(private val storeFactory: StoreFactory) : KoinComponent {
    val currentEventController: CurrentEventController by inject()

    @OptIn(ExperimentalMviKotlinApi::class)
    fun create(): FreeSelectStore =
        object : FreeSelectStore,
            Store<FreeSelectStore.Intent, FreeSelectStore.State, Nothing> by storeFactory.create(
                name = "FreeSelectStore",
                initialState = FreeSelectStore.State.defaultState,
                bootstrapper = coroutineBootstrapper {},
                executorFactory = ::ExecutorImpl,
                reducer = ReducerImpl
            ) {}

    private sealed interface Message {
        object Success : Message
        object Load : Message
    }

    private inner class ExecutorImpl() :
        CoroutineExecutor<FreeSelectStore.Intent, Nothing, FreeSelectStore.State, Message, Nothing>() {

        override fun executeIntent(
            intent: FreeSelectStore.Intent,
            getState: () -> FreeSelectStore.State
        ) {
            when (intent) {
                is FreeSelectStore.Intent.OnFreeSelectRequest -> freeRoom(intent.close)
                is FreeSelectStore.Intent.OnCloseWindowRequest -> intent.close?.invoke()
            }
        }

        private fun freeRoom(close: (() -> Unit)?) = scope.launch() {
            dispatch(Message.Load)
            if (currentEventController.cancelCurrentEvent() is Either.Success) {
                close?.invoke()
                dispatch(Message.Success)
            }
            else TODO("Maksim Mishenko: add error handler")
        }
    }

    private object ReducerImpl : Reducer<FreeSelectStore.State, Message> {
        override fun FreeSelectStore.State.reduce(msg: Message) =
            when (msg) {
                is Message.Load -> copy(isLoad = true)
                is Message.Success -> copy(isLoad = false)
            }
    }
}