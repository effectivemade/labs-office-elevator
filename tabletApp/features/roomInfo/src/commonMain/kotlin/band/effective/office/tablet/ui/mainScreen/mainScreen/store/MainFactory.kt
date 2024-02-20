package band.effective.office.tablet.ui.mainScreen.mainScreen.store

import android.os.Build
import androidx.annotation.RequiresApi
import band.effective.office.network.model.Either
import band.effective.office.tablet.domain.model.ErrorWithData
import band.effective.office.tablet.domain.model.EventInfo
import band.effective.office.tablet.domain.model.RoomInfo
import band.effective.office.tablet.domain.useCase.CheckSettingsUseCase
import band.effective.office.tablet.domain.useCase.RoomInfoUseCase
import band.effective.office.tablet.domain.useCase.SelectRoomUseCase
import band.effective.office.tablet.domain.useCase.TimerUseCase
import band.effective.office.tablet.domain.useCase.UpdateUseCase
import band.effective.office.tablet.ui.mainScreen.mainScreen.MainComponent
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineBootstrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.Calendar
import java.util.GregorianCalendar
import kotlin.time.Duration.Companion.seconds

class MainFactory(
    private val storeFactory: StoreFactory,
    private val navigate: (MainComponent.ModalWindowsConfig) -> Unit,
    private val updateRoomInfo: (RoomInfo) -> Unit,
    private val updateDate: (Calendar) -> Unit
) : KoinComponent {

    private val roomInfoUseCase: RoomInfoUseCase by inject()
    private val checkSettingsUseCase: CheckSettingsUseCase by inject()
    private val updateUseCase: UpdateUseCase by inject()
    private val timerUseCase: TimerUseCase by inject()
    private val selectRoomUseCase: SelectRoomUseCase by inject()

    @OptIn(ExperimentalMviKotlinApi::class)
    fun create(): MainStore =
        object : MainStore,
            Store<MainStore.Intent, MainStore.State, MainStore.Label> by storeFactory.create(
                name = "MainStore",
                initialState = MainStore.State.defaultState,
                bootstrapper = coroutineBootstrapper {
                    launch {
                        if (checkSettingsUseCase().isEmpty()) {
                            dispatch(Action.OnSettings)
                        } else {
                            dispatch(Action.OnLoad(RoomInfoEither = roomInfoUseCase()))
                        }
                    }
                    launch(Dispatchers.IO) {
                        updateUseCase.updateFlow().collect {
                            delay(1.seconds)
                            withContext(Dispatchers.Main) {
                                // dispatch(Action.OnLoad(RoomInfoEither = roomInfoUseCase())) //TODO()
                            }
                        }
                    }
                    timerUseCase.timer(this, 1.seconds) { _ ->
                        withContext(Dispatchers.Main) {
                            dispatch(Action.OnUpdateTimer)
                        }
                    }
                    launch(Dispatchers.Main) {
                        roomInfoUseCase.subscribe(this).collect {
                            dispatch(Action.OnUpdateRoomInfo)
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
        object UpdateTimer : Message
        data class UpdateDate(val newDate: Calendar) : Message
    }

    private sealed interface Action {
        data class OnLoad(val RoomInfoEither: Either<ErrorWithData<List<RoomInfo>>, List<RoomInfo>>) :
            Action

        object OnSettings : Action
        object OnUpdateTimer : Action
        object OnUpdateRoomInfo : Action
        object RefreshDate : Action
    }

    private inner class ExecutorImpl() :
        CoroutineExecutor<MainStore.Intent, Action, MainStore.State, Message, MainStore.Label>() {
        @RequiresApi(Build.VERSION_CODES.N)
        override fun executeIntent(intent: MainStore.Intent, getState: () -> MainStore.State) {
            when (intent) {
                is MainStore.Intent.OnBookingOtherRoomRequest -> dispatch(Message.BookingOtherRoom)
                is MainStore.Intent.OnOpenFreeRoomModal ->
                    navigate(MainComponent.ModalWindowsConfig.FreeRoom(getState().run { roomList[indexSelectRoom].currentEvent!! }))

                is MainStore.Intent.OnDisconnectChange -> dispatch(Message.UpdateDisconnect(intent.newValue))
                is MainStore.Intent.RebootRequest -> reboot(getState(), true)
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
                is MainStore.Intent.OnFastBooking -> {
                    val state = getState()
                    val currentRoom = state.run { roomList[indexSelectRoom] }
                    val selectRoom =
                        selectRoomUseCase.getRoom(
                            currentRoom = currentRoom,
                            rooms = state.roomList,
                            minEventDuration = intent.minDuration
                        )
                    val event = EventInfo.emptyEvent.copy(
                        startTime = GregorianCalendar(),
                        finishTime = GregorianCalendar().apply {
                            add(
                                Calendar.MINUTE,
                                intent.minDuration
                            )
                        })
                    if (selectRoom != null) {
                        navigate(
                            MainComponent.ModalWindowsConfig.UpdateEvent(
                                event = event,
                                room = selectRoom.name
                            )
                        )
                    }
                    publish(MainStore.Label.ShowToast(selectRoom?.name ?: "Нет свободной комнаты"))
                }

                is MainStore.Intent.OnUpdateSelectDate -> {
                    val newDate = (getState().selectDate.clone() as Calendar).apply {
                        add(
                            Calendar.DAY_OF_YEAR,
                            intent.updateInDays
                        )
                    }
                    dispatch(Message.UpdateDate(newDate))
                    updateDate(newDate)
                }

                MainStore.Intent.OnResetSelectDate -> {
                    updateDate(GregorianCalendar())
                    dispatch(Message.UpdateDate(GregorianCalendar()))
                }
            }
        }

        fun reboot(state: MainStore.State, refresh: Boolean = false) = scope.launch {
            //if (!refresh) dispatch(Message.Reboot)
            if (refresh) {
                if (!state.isData) {
                    dispatch(Message.Reboot)
                }
                withContext(Dispatchers.IO) {
                    roomInfoUseCase.updateCaсhe()
                }
            }
            when (val either = roomInfoUseCase()) {
                is Either.Error -> {
                    val save = either.error.saveData
                    if (!state.isData) {
                        dispatch(
                            Message.Load(
                                isSuccess = false,
                                roomList = save ?: listOf(RoomInfo.defaultValue),
                                indexSelectRoom = 0
                            )
                        )
                    } else {
                        dispatch(Message.UpdateDisconnect(true))
                    }
                }

                is Either.Success -> {
                    dispatch(Message.UpdateDisconnect(false))
                    dispatch(
                        Message.Load(
                            isSuccess = true,
                            roomList = either.data,
                            indexSelectRoom = state.indexRoom()
                        )
                    )
                    updateRoomInfo(either.data[state.indexRoom()])
                }
            }
        }

        override fun executeAction(action: Action, getState: () -> MainStore.State) {
            when (action) {
                is Action.OnLoad -> {
                    when (val either = action.RoomInfoEither) {
                        is Either.Error -> {
                            val save = either.error.saveData
                            dispatch(
                                Message.Load(
                                    isSuccess = false,
                                    roomList = save ?: listOf(RoomInfo.defaultValue),
                                    indexSelectRoom = 0
                                )
                            )
                        }

                        is Either.Success -> {
                            either.data.apply {
                                dispatch(
                                    Message.Load(
                                        isSuccess = true,
                                        roomList = this,
                                        indexSelectRoom = getState().indexRoom()
                                    )
                                )
                                reboot(getState())
                            }
                        }
                    }
                }

                is Action.OnSettings -> dispatch(Message.OnSettings)
                Action.OnUpdateTimer -> dispatch(Message.UpdateTimer)
                Action.OnUpdateRoomInfo -> reboot(getState())
                Action.RefreshDate -> dispatch(Message.UpdateDate(GregorianCalendar())).apply {
                    updateDate(
                        GregorianCalendar()
                    )
                }
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
                    indexSelectRoom = message.indexSelectRoom,
                    timeToNextEvent = calcTimeToNextEvent()
                )

                is Message.UpdateDisconnect -> copy(
                    isDisconnect = message.newValue,
                    timeToNextEvent = calcTimeToNextEvent()
                )

                is Message.Reboot -> copy(
                    isError = false,
                    isLoad = true,
                    timeToNextEvent = calcTimeToNextEvent()
                )

                is Message.OnSettings -> copy(
                    isError = false,
                    isLoad = false,
                    isData = false,
                    isSettings = true
                )

                is Message.SelectRoom -> copy(
                    indexSelectRoom = message.index,
                    timeToNextEvent = calcTimeToNextEvent()
                )

                Message.UpdateTimer -> copy(timeToNextEvent = calcTimeToNextEvent())
                is Message.UpdateDate -> copy(selectDate = message.newDate)
            }

        private fun MainStore.State.calcTimeToNextEvent() =
            roomList.getOrNull(indexSelectRoom)?.currentEvent
                ?.run { ((finishTime.time.time - GregorianCalendar().time.time) / 60000).toInt() }
                ?: 0
    }
}