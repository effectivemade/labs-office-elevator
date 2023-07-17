package band.effective.office.tablet.ui.selectRoomScreen.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineBootstrapper
import org.koin.core.component.KoinComponent

class SelectRoomStoreFactory(private val storeFactory: StoreFactory) : KoinComponent {
    @OptIn(ExperimentalMviKotlinApi::class)
    fun create(): SelectRoomStore =
        object : SelectRoomStore, Store<SelectRoomStore.Intent, SelectRoomStore.State, Nothing>
                by storeFactory.create(
            name = "SelectStore",
            initialState = SelectRoomStore.State.defaultState,
            bootstrapper = coroutineBootstrapper {},
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed interface Message {
        object BookingRoom : Message
        object CloseModal : Message
    }

    private inner class ExecutorImpl:
        CoroutineExecutor<SelectRoomStore.Intent, Nothing, SelectRoomStore.State, Message, Nothing>() {
        override fun executeIntent(intent: SelectRoomStore.Intent, getState: () -> SelectRoomStore.State) {
            when (intent) {
                is SelectRoomStore.Intent.BookingRoom -> dispatch(Message.BookingRoom)
                is SelectRoomStore.Intent.CloseModal -> dispatch(Message.CloseModal)
            }
        }
    }

    private object ReducerImpl : Reducer<SelectRoomStore.State, Message> {
        override fun SelectRoomStore.State.reduce(message: Message): SelectRoomStore.State =
            when (message) {
                is Message.BookingRoom -> copy(isData = false)
                is Message.CloseModal -> SelectRoomStore.State.defaultState
            }
    }



}