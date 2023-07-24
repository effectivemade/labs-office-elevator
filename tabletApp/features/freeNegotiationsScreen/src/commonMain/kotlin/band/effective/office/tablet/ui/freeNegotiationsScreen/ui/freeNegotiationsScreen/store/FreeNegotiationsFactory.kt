package band.effective.office.tablet.ui.freeNegotiationsScreen.ui.freeNegotiationsScreen.store

import band.effective.office.tablet.domain.model.RoomInfo
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineBootstrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class FreeNegotiationsFactory(private val storeFactory: StoreFactory): KoinComponent {

    @OptIn(ExperimentalMviKotlinApi::class)
    fun create(): FreeNegotiationsStore =
        object : FreeNegotiationsStore,
            Store<FreeNegotiationsStore.Intent, FreeNegotiationsStore.State, Nothing> by storeFactory.create(
                name = "FreeNegotiationsStore",
                initialState = FreeNegotiationsStore.State.defaultState,
                bootstrapper = coroutineBootstrapper {
                    launch() {
                        //TODO
                    }
                },
                executorFactory = ::ExecutorImpl,
                reducer = ReducerImpl
            ) {}

    private sealed interface Action {
        data class GetFreeRoomsInfo(val roomsInfo: List<RoomInfo>) : Action
    }

    private sealed interface Message {
        data class GetFreeRoomsInfo(val roomsInfo: List<RoomInfo>) : Message
        object BookRoom : Message
        object MainScreen : Message
        object CloseModal : Message
    }

    private inner class ExecutorImpl() :
        CoroutineExecutor<FreeNegotiationsStore.Intent, Action, FreeNegotiationsStore.State, Message, Nothing>() {
        override fun executeIntent(intent: FreeNegotiationsStore.Intent, getState: () -> FreeNegotiationsStore.State) {
            when (intent) {
                is FreeNegotiationsStore.Intent.OnBookingRoom -> dispatch(Message.BookRoom)
                is FreeNegotiationsStore.Intent.OnMainScreen -> dispatch(Message.MainScreen)
                is FreeNegotiationsStore.Intent.CloseModal -> dispatch(Message.CloseModal)
            }
        }

        override fun executeAction(action: Action, getState: () -> FreeNegotiationsStore.State) {
            when (action) {
                is Action.GetFreeRoomsInfo -> dispatch(Message.GetFreeRoomsInfo(action.roomsInfo))
            }
        }
    }

    private object ReducerImpl : Reducer<FreeNegotiationsStore.State, Message> {
        override fun FreeNegotiationsStore.State.reduce(message: Message): FreeNegotiationsStore.State =
            when (message) {
                is Message.BookRoom-> copy(showBookingModal = true)
                is Message.MainScreen -> copy()
                is Message.GetFreeRoomsInfo -> copy(
                    listFreeRooms = message.roomsInfo,
                    isData = true,
                    isLoad = false
                )

                is Message.CloseModal -> copy(showBookingModal = false)
            }
    }
}