package band.effective.office.tablet.ui.mainScreen.mainScreen.store

import band.effective.office.tablet.domain.model.Either
import band.effective.office.tablet.domain.useCase.RoomInfoUseCase
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineBootstrapper
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MainFactory(private val storeFactory: StoreFactory) : KoinComponent {

    private val roomInfoUseCase: RoomInfoUseCase by inject()

    @OptIn(ExperimentalMviKotlinApi::class)
    fun create(): MainStore =
        object : MainStore,
            Store<MainStore.Intent, MainStore.State, Nothing> by storeFactory.create(
                name = "MainStore",
                initialState = MainStore.State.defaultState,
                bootstrapper = coroutineBootstrapper {
                    launch {
                        dispatch(
                            Action.OnLoad(
                                roomInfoUseCase() is Either.Success
                            )
                        )
                    }
                },
                executorFactory = ::ExecutorImpl,
                reducer = ReducerImpl
            ) {}

    private sealed interface Message {
        object BookingCurrentRoom : Message
        object BookingOtherRoom : Message
        object CloseModal : Message
        object OpenFreeModal : Message {

        }

        data class Load(val isSuccess: Boolean) : Message
    }

    private sealed interface Action {
        data class OnLoad(val isSuccess: Boolean) : Action
    }

    private inner class ExecutorImpl() :
        CoroutineExecutor<MainStore.Intent, Action, MainStore.State, Message, Nothing>() {
        override fun executeIntent(intent: MainStore.Intent, getState: () -> MainStore.State) {
            when (intent) {
                is MainStore.Intent.OnBookingCurrentRoomRequest -> dispatch(Message.BookingCurrentRoom)
                is MainStore.Intent.OnBookingOtherRoomRequest -> dispatch(Message.BookingOtherRoom)
                is MainStore.Intent.CloseModal -> dispatch(Message.CloseModal)
                is MainStore.Intent.OnOpenFreeRoomModal -> dispatch(Message.OpenFreeModal)
            }
        }

        override fun executeAction(action: Action, getState: () -> MainStore.State) {
            when (action) {
                is Action.OnLoad -> dispatch(Message.Load(action.isSuccess))
            }
        }
    }

    private object ReducerImpl : Reducer<MainStore.State, Message> {
        override fun MainStore.State.reduce(message: Message): MainStore.State =
            when (message) {
                is Message.BookingCurrentRoom -> copy(showBookingModal = true)
                is Message.BookingOtherRoom -> copy()
                is Message.CloseModal -> copy(showBookingModal = false, showFreeModal = false)
                is Message.Load -> copy(
                    isLoad = false,
                    isData = message.isSuccess,
                    isError = !message.isSuccess
                )

                is Message.OpenFreeModal -> copy(showFreeModal = true)
            }
    }
}