package band.effective.office.tablet.ui.freeNegotiationsScreen.ui.freeNegotiationsScreen.store

import band.effective.office.tablet.domain.model.Booking
import band.effective.office.tablet.domain.model.RoomInfo
import band.effective.office.tablet.domain.useCase.RoomInfoUseCase
import band.effective.office.tablet.ui.freeNegotiationsScreen.ui.freeNegotiationsScreen.roomUiState.RoomInfoUiState
import band.effective.office.tablet.ui.freeNegotiationsScreen.ui.freeNegotiationsScreen.roomUiState.RoomState
import band.effective.office.tablet.ui.freeNegotiationsScreen.ui.freeNegotiationsScreen.timer.Timer
import band.effective.office.tablet.ui.freeNegotiationsScreen.ui.freeNegotiationsScreen.uiComponents.checkDuration
import band.effective.office.tablet.ui.freeNegotiationsScreen.ui.freeNegotiationsScreen.uiComponents.getDurationRelativeCurrentTime
import band.effective.office.tablet.ui.selectRoomScreen.uiComponents.getLengthEvent
import band.effective.office.tablet.utils.unbox
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineBootstrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.Calendar

class FreeNegotiationsStoreFactory(private val storeFactory: StoreFactory) : KoinComponent {

    private val timer = Timer()
    private val roomInfoUseCase: RoomInfoUseCase by inject()

    @OptIn(ExperimentalMviKotlinApi::class)
    fun create(): FreeNegotiationsStore =
        object : FreeNegotiationsStore,
            Store<FreeNegotiationsStore.Intent, FreeNegotiationsStore.State, Nothing> by storeFactory.create(
                name = "FreeNegotiationsStore",
                initialState = FreeNegotiationsStore.State.defaultState,
                bootstrapper = coroutineBootstrapper {
                    launch() {
                        var error = false
                        val pluto = roomInfoUseCase.invoke(room = "Pluto").unbox(
                            errorHandler = {
                                error = true
                                dispatch(Action.ResponseError)
                                it.saveData ?: RoomInfo.defaultValue
                            },
                            successHandler = {
                                it
                            }
                        )

                        val moon = roomInfoUseCase.invoke(room = "Moon").unbox(
                            errorHandler = {
                                error = true
                                dispatch(Action.ResponseError)
                                it.saveData ?: RoomInfo.defaultValue
                            },
                            successHandler = {
                                it
                            }
                        )

                        val antares = roomInfoUseCase.invoke(room = "Antares").unbox(
                            errorHandler = {
                                error = true
                                dispatch(Action.ResponseError)
                                it.saveData ?: RoomInfo.defaultValue
                            },
                            successHandler = {
                                it
                            }
                        )

                        val sun = roomInfoUseCase.invoke(room = "Sun").unbox(
                            errorHandler = {
                                error = true
                                dispatch(Action.ResponseError)
                                it.saveData ?: RoomInfo.defaultValue
                            },
                            successHandler = {
                                it
                            }
                        )

                        if (!error) {
                            dispatch(Action.GetFreeRoomsInfo(listOf(pluto, moon, antares, sun)))
                            timer.subscribe {
                                dispatch(
                                    Action.UpdateChangeEventTime
                                )
                            }
                            timer.startTimer()
                        }
                    }
                },
                executorFactory = ::ExecutorImpl,
                reducer = ReducerImpl
            ) {}

    private sealed interface Action {
        data class GetFreeRoomsInfo(val roomsInfo: List<RoomInfo>) : Action
        object UpdateChangeEventTime : Action
        object ResponseError : Action
    }

    private sealed interface Message {
        data class GetFreeRoomsInfo(val roomsInfo: List<RoomInfoUiState>) : Message
        data class SetBooking(val bookingInfo: Booking) : Message
        data class BookRoom(val nameRoom: String, val maxDuration: Int) : Message
        object CloseModal : Message
        data class UpdateChangeEventTime(val roomsInfo: List<RoomInfoUiState>) : Message
        object ResponseError : Message

    }

    private inner class ExecutorImpl() :
        CoroutineExecutor<FreeNegotiationsStore.Intent, Action, FreeNegotiationsStore.State, Message, Nothing>() {
        override fun executeIntent(
            intent: FreeNegotiationsStore.Intent,
            getState: () -> FreeNegotiationsStore.State
        ) {
            when (intent) {
                is FreeNegotiationsStore.Intent.OnBookingRoom ->
                    dispatch(
                        Message.BookRoom(
                            nameRoom = intent.name,
                            maxDuration = intent.maxDuration
                        )
                    )

                is FreeNegotiationsStore.Intent.OnMainScreen -> {
                    timer.close()
                }

                is FreeNegotiationsStore.Intent.CloseModal -> dispatch(Message.CloseModal)
                is FreeNegotiationsStore.Intent.SetBooking -> dispatch(Message.SetBooking(intent.bookingInfo))
            }
        }

        override fun executeAction(action: Action, getState: () -> FreeNegotiationsStore.State) {
            when (action) {
                is Action.GetFreeRoomsInfo -> {
                    val roomsInfoUi = mutableListOf<RoomInfoUiState>()
                    action.roomsInfo.forEach {
                        val roomState = getStateBusyRoom(it, getState())
                        roomsInfoUi.add(
                            RoomInfoUiState(
                                room = it,
                                state = roomState,
                                changeEventTime = getChangeEventTime(roomState, it)
                            )
                        )
                    }
                    roomsInfoUi.sortBy { it.state.codeState }
                    dispatch(Message.GetFreeRoomsInfo(roomsInfoUi.toList()))
                }

                is Action.UpdateChangeEventTime -> {
                    scope.launch {
                        val listRooms = updateTimeEvent(getState().listRooms, getState())
                        dispatch(Message.UpdateChangeEventTime(listRooms))
                    }
                }

                is Action.ResponseError -> {
                    dispatch(Message.ResponseError)
                }
            }
        }

        suspend fun updateTimeEvent(
            listRooms: List<RoomInfoUiState>,
            state: FreeNegotiationsStore.State
        ): List<RoomInfoUiState> {
            val newListRooms = mutableListOf<RoomInfoUiState>()
            listRooms.forEach {
                if (it.changeEventTime > 0) {
                    val roomNewTime = it.copy(changeEventTime = it.changeEventTime - 1)
                    newListRooms.add(updateState(roomNewTime, state))
                } else {
                    newListRooms.add(it)
                }
            }
            return newListRooms.toList().sortedBy { it.state.codeState }
        }

        suspend fun updateState(
            roomInfo: RoomInfoUiState,
            state: FreeNegotiationsStore.State
        ): RoomInfoUiState {
            if (roomInfo.state == RoomState.FREE && roomInfo.room.eventList.isNotEmpty() && !checkDuration
                    (
                    roomInfo.room.eventList.first().startTime,
                    state.chosenDurationBooking
                )
            ) {
                return roomInfo.copy(state = RoomState.SOON_BUSY)
            }
            if (roomInfo.changeEventTime == 0) {
                return withContext(Dispatchers.IO) {
                    val room = roomInfoUseCase.invoke(room = "Pluto_upd").unbox(
                        errorHandler = {
                            it.saveData ?: RoomInfo.defaultValue
                        },
                        successHandler = {
                            it
                        }
                    )
                    val roomState = getStateBusyRoom(room, state)
                    return@withContext RoomInfoUiState(
                        room = room,
                        state = roomState,
                        changeEventTime = getChangeEventTime(roomState, room)
                    )
                }
            } else {
            return roomInfo
            }
        }
    }

    fun getStateBusyRoom(room: RoomInfo, state: FreeNegotiationsStore.State): RoomState {
        return when {
            room.currentEvent != null -> RoomState.BUSY
            room.eventList.isNotEmpty() && !checkDuration
                (
                room.eventList.first().startTime,
                state.chosenDurationBooking
            ) -> RoomState.SOON_BUSY

            else -> RoomState.FREE
        }
    }

    fun getChangeEventTime(roomState: RoomState, room: RoomInfo): Int {
        return when {
            roomState == RoomState.BUSY -> getDurationRelativeCurrentTime(room.currentEvent!!.finishTime)
            roomState == RoomState.SOON_BUSY || (roomState == RoomState.FREE && room.eventList.isNotEmpty()) ->
                getDurationRelativeCurrentTime(room.eventList.first().startTime)

            else -> -1
        }
    }

    private object ReducerImpl : Reducer<FreeNegotiationsStore.State, Message> {
        override fun FreeNegotiationsStore.State.reduce(message: Message): FreeNegotiationsStore.State =
            when (message) {
                is Message.BookRoom -> copy(
                    nameBookingRoom = message.nameRoom,
                    currentTime = Calendar.getInstance(),
                    realDurationBooking = if (message.maxDuration != -1) {
                        chosenDurationBooking.coerceAtMost(message.maxDuration)
                    } else {
                        this.chosenDurationBooking
                    },
                    showBookingModal = true
                )

                is Message.GetFreeRoomsInfo -> copy(
                    isLoad = false,
                    isData = true,
                    listRooms = message.roomsInfo
                )

                is Message.SetBooking -> copy(
                    organizer = message.bookingInfo.eventInfo.organizer,
                    chosenDurationBooking = getLengthEvent(
                        start = message.bookingInfo.eventInfo.startTime,
                        finish = message.bookingInfo.eventInfo.finishTime
                    ),
                    nameCurrentRoom = message.bookingInfo.nameRoom
                )

                is Message.CloseModal -> copy(showBookingModal = false)
                is Message.UpdateChangeEventTime -> copy(
                    listRooms = message.roomsInfo
                )

                is Message.ResponseError -> copy(
                    isLoad = false,
                    error = ""
                )
            }
    }
}