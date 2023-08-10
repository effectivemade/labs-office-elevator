package band.effective.office.tablet.ui.mainScreen.mainScreen.store

import band.effective.office.network.model.Either
import band.effective.office.tablet.domain.model.EventInfo
import band.effective.office.tablet.domain.model.Settings
import band.effective.office.tablet.domain.useCase.CheckSettingsUseCase
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
    private val checkSettingsUseCase: CheckSettingsUseCase by inject()

    @OptIn(ExperimentalMviKotlinApi::class)
    fun create(): MainStore =
        object : MainStore,
            Store<MainStore.Intent, MainStore.State, Nothing> by storeFactory.create(
                name = "MainStore",
                initialState = MainStore.State.defaultState,
                bootstrapper = coroutineBootstrapper {
                    launch {
                        if(checkSettingsUseCase().isEmpty()){
                            dispatch(Action.OnSettings)
                        } else {
                            dispatch(
                                Action.OnLoad(
                                    roomInfoUseCase(checkSettingsUseCase()) is Either.Success
                                )
                            )
                        }
                    }
                },
                executorFactory = ::ExecutorImpl,
                reducer = ReducerImpl
            ) {}

    private sealed interface Message {
        object BookingCurrentRoom : Message
        object BookingOtherRoom : Message
        object CloseModal : Message
        object OpenFreeModal : Message
        object OpenDateTimePickerModal : Message
        data class Load(val isSuccess: Boolean) : Message
        data class UpdateDisconnect(val newValue: Boolean) : Message
        object Reboot : Message

        data class OpenUpdateModal(val eventInfo: EventInfo) : Message
        object OnSettings : Message
    }

    private sealed interface Action {
        data class OnLoad(val isSuccess: Boolean) : Action
        object OnSettings : Action
    }

    private inner class ExecutorImpl() :
        CoroutineExecutor<MainStore.Intent, Action, MainStore.State, Message, Nothing>() {
        override fun executeIntent(intent: MainStore.Intent, getState: () -> MainStore.State) {
            when (intent) {
                is MainStore.Intent.OnBookingCurrentRoomRequest -> dispatch(Message.BookingCurrentRoom)
                is MainStore.Intent.OnBookingOtherRoomRequest -> dispatch(Message.BookingOtherRoom)
                is MainStore.Intent.CloseModal -> dispatch(Message.CloseModal)
                is MainStore.Intent.OnOpenFreeRoomModal -> dispatch(Message.OpenFreeModal)
                is MainStore.Intent.OnOpenDateTimePickerModal -> dispatch(Message.OpenDateTimePickerModal)
                is MainStore.Intent.OnDisconnectChange -> dispatch(Message.UpdateDisconnect(intent.newValue))
                is MainStore.Intent.RebootRequest -> reboot()
                is MainStore.Intent.OnChangeEventRequest -> dispatch(Message.OpenUpdateModal(intent.eventInfo))
            }
        }

        fun reboot() = scope.launch {
            dispatch(Message.Reboot)
            dispatch(Message.Load(roomInfoUseCase(checkSettingsUseCase()) is Either.Success))
        }

        override fun executeAction(action: Action, getState: () -> MainStore.State) {
            when (action) {
                is Action.OnLoad -> dispatch(Message.Load(action.isSuccess))
                is Action.OnSettings -> dispatch(Message.OnSettings)
            }
        }
    }

    private object ReducerImpl : Reducer<MainStore.State, Message> {
        override fun MainStore.State.reduce(message: Message): MainStore.State =
            when (message) {
                is Message.BookingCurrentRoom -> copy(showBookingModal = true)
                is Message.BookingOtherRoom -> copy()
                is Message.CloseModal -> copy(
                    showBookingModal = false,
                    showFreeModal = false,
                    showDateTimePickerModal = false,
                    showUpdateModal = false
                )

                is Message.Load -> copy(
                    isLoad = false,
                    isData = message.isSuccess,
                    isError = !message.isSuccess
                )

                is Message.OpenFreeModal -> copy(showFreeModal = true)
                is Message.UpdateDisconnect -> copy(isDisconnect = message.newValue)
                is Message.Reboot -> copy(
                    isError = false,
                    isLoad = true
                )
                is Message.OpenDateTimePickerModal -> copy(showDateTimePickerModal = true)
                is Message.OpenUpdateModal -> copy(showUpdateModal = true, updatedEvent = message.eventInfo)
                is Message.OnSettings -> copy(
                    isError = false,
                    isLoad = false,
                    isData = false,
                    isSettings = true
                )
            }
    }
}