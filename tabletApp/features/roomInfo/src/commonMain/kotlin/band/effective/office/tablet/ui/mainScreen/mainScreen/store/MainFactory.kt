package band.effective.office.tablet.ui.mainScreen.mainScreen.store

import android.os.Build
import androidx.annotation.RequiresApi
import band.effective.office.network.model.Either
import band.effective.office.tablet.domain.CurrentEventController
import band.effective.office.tablet.domain.model.ErrorWithData
import band.effective.office.tablet.domain.model.RoomInfo
import band.effective.office.tablet.domain.useCase.CheckSettingsUseCase
import band.effective.office.tablet.domain.useCase.RoomInfoUseCase
import band.effective.office.tablet.ui.mainScreen.mainScreen.MainComponent
import band.effective.office.tablet.utils.unbox
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineBootstrapper
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MainFactory(
    private val storeFactory: StoreFactory,
    private val navigate: (MainComponent.ModalWindowsConfig) -> Unit,
    private val updateRoomInfo: (RoomInfo) -> Unit
) : KoinComponent {

    private val roomInfoUseCase: RoomInfoUseCase by inject()
    private val checkSettingsUseCase: CheckSettingsUseCase by inject()
    private val currentEventController: CurrentEventController by inject()

    @OptIn(ExperimentalMviKotlinApi::class)
    fun create(): MainStore =
        object : MainStore,
            Store<MainStore.Intent, MainStore.State, Nothing> by storeFactory.create(
                name = "MainStore",
                initialState = MainStore.State.defaultState,
                bootstrapper = coroutineBootstrapper {
                    launch {
                        if (checkSettingsUseCase().isEmpty()) {
                            dispatch(Action.OnSettings)
                        } else {
                            dispatch(Action.OnLoad(isSuccess = roomInfoUseCase()))
                        }
                    }
                },
                executorFactory = ::ExecutorImpl,
                reducer = ReducerImpl
            ) {}

    private sealed interface Message {
        object BookingOtherRoom : Message
        data class Load(
            val isSuccess: Boolean,
            val roomList: List<RoomInfo>,
            val indexSelectRoom: Int
        ) : Message

        data class UpdateDisconnect(val newValue: Boolean) : Message
        object Reboot : Message
        object OnSettings : Message
        data class SelectRoom(val index: Int) : Message
    }

    private sealed interface Action {
        data class OnLoad(val isSuccess: Either<ErrorWithData<List<RoomInfo>>, List<RoomInfo>>) :
            Action

        object OnSettings : Action
    }

    private inner class ExecutorImpl() :
        CoroutineExecutor<MainStore.Intent, Action, MainStore.State, Message, Nothing>() {
        @RequiresApi(Build.VERSION_CODES.N)
        override fun executeIntent(intent: MainStore.Intent, getState: () -> MainStore.State) {
            when (intent) {
                is MainStore.Intent.OnBookingOtherRoomRequest -> dispatch(Message.BookingOtherRoom)
                is MainStore.Intent.OnOpenFreeRoomModal ->
                    navigate(MainComponent.ModalWindowsConfig.FreeRoom(getState().run { roomList[indexSelectRoom].currentEvent!! }))

                is MainStore.Intent.OnDisconnectChange -> dispatch(Message.UpdateDisconnect(intent.newValue))
                is MainStore.Intent.RebootRequest -> reboot(getState())
                is MainStore.Intent.OnChangeEventRequest -> navigate(
                    MainComponent.ModalWindowsConfig.UpdateEvent(
                        event = intent.eventInfo,
                        room = getState().run { roomList[indexSelectRoom].name }
                    )
                )

                is MainStore.Intent.OnSelectRoom -> dispatch(Message.SelectRoom(intent.index.apply {
                    updateRoomInfo(getState().roomList[this])
                }))
                MainStore.Intent.OnUpdate -> reboot(getState(), true)
            }
        }

        fun reboot(state: MainStore.State, refresh: Boolean = false) = scope.launch {
            if (!refresh) dispatch(Message.Reboot)
            if (refresh) roomInfoUseCase.updateCashe()
            roomInfoUseCase().unbox({ it.saveData ?: listOf() })
                .apply {
                    dispatch(
                        Message.Load(
                            isSuccess = isNotEmpty(), roomList = this,
                            indexSelectRoom = state.indexRoom()
                        )
                    )
                    updateRoomInfo(this[state.indexRoom()])
                }
        }

        override fun executeAction(action: Action, getState: () -> MainStore.State) {
            when (action) {
                is Action.OnLoad -> action.isSuccess.unbox({ it.saveData ?: listOf() })
                    .apply {
                        dispatch(
                            Message.Load(
                                isSuccess = isNotEmpty(),
                                roomList = this,
                                indexSelectRoom = getState().indexRoom()
                            )
                        )
                        reboot(getState())
                    }

                is Action.OnSettings -> dispatch(Message.OnSettings)
            }
        }

        private fun MainStore.State.indexRoom() =
            roomList.indexOfFirst { it.name == checkSettingsUseCase() }.run {
                if (this < 0) 0 else this
            }
    }

    private object ReducerImpl : Reducer<MainStore.State, Message> {
        override fun MainStore.State.reduce(message: Message): MainStore.State =
            when (message) {
                is Message.BookingOtherRoom -> copy()

                is Message.Load -> copy(
                    isLoad = false,
                    isData = message.isSuccess,
                    isError = !message.isSuccess,
                    roomList = if (message.isSuccess) message.roomList else roomList,
                    indexSelectRoom = message.indexSelectRoom
                )

                is Message.UpdateDisconnect -> copy(isDisconnect = message.newValue)
                is Message.Reboot -> copy(
                    isError = false,
                    isLoad = true
                )

                is Message.OnSettings -> copy(
                    isError = false,
                    isLoad = false,
                    isData = false,
                    isSettings = true
                )

                is Message.SelectRoom -> copy(indexSelectRoom = message.index)
            }
    }
}