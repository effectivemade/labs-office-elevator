package band.effective.office.tablet.ui.freeNegotiationsScreen.ui.freeNegotiationsScreen.store

import band.effective.office.network.model.Either
import band.effective.office.tablet.domain.model.Booking
import band.effective.office.tablet.domain.model.RoomInfo
import band.effective.office.tablet.domain.useCase.CheckSettingsUseCase
import band.effective.office.tablet.domain.useCase.RoomInfoUseCase
import band.effective.office.tablet.ui.freeNegotiationsScreen.ui.freeNegotiationsScreen.roomUiState.RoomInfoUiState
import band.effective.office.tablet.ui.freeNegotiationsScreen.ui.freeNegotiationsScreen.roomUiState.RoomState
import band.effective.office.tablet.ui.freeNegotiationsScreen.ui.freeNegotiationsScreen.timer.Timer
import band.effective.office.tablet.ui.freeNegotiationsScreen.ui.freeNegotiationsScreen.uiComponents.checkCurrentTime
import band.effective.office.tablet.ui.freeNegotiationsScreen.ui.freeNegotiationsScreen.uiComponents.checkDuration
import band.effective.office.tablet.ui.freeNegotiationsScreen.ui.freeNegotiationsScreen.uiComponents.getDurationRelativeCurrentTime
import band.effective.office.tablet.ui.selectRoomScreen.uiComponents.getLengthEvent
import band.effective.office.tablet.utils.oneDay
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
    private val checkSettingsUseCase: CheckSettingsUseCase by inject()

    @OptIn(ExperimentalMviKotlinApi::class)
    fun create(): FreeNegotiationsStore =
        object : FreeNegotiationsStore,
            Store<FreeNegotiationsStore.Intent, FreeNegotiationsStore.State, Nothing> by storeFactory.create(
                name = "FreeNegotiationsStore",
                initialState = FreeNegotiationsStore.State.defaultState,
                bootstrapper = coroutineBootstrapper {
                    launch() {
                        val response = roomInfoUseCase.getOtherRoom(checkSettingsUseCase())
                        when (response) {
                            is Either.Error -> dispatch(Action.FailLoad)
                            is Either.Success -> {
                                dispatch(Action.GetFreeRoomsInfo(response.data))
                                timer.subscribe {
                                    dispatch(
                                        Action.UpdateChangeEventTime
                                    )
                                }
                                timer.startTimer()
                            }
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
        object FailLoad : Action
    }

    private sealed interface Message {
        data class SetBooking(val bookingInfo: Booking) : Message
        data class GetFreeRoomsInfo(val roomsInfo: List<RoomInfoUiState>) : Message
        data class BookRoom(val nameRoom: RoomInfoUiState, val maxDuration: Int) : Message
        object CloseModal : Message
        data class UpdateListRooms(val roomsInfo: List<RoomInfoUiState>) : Message
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
                is FreeNegotiationsStore.Intent.SetBooking -> {
                    dispatch(Message.SetBooking(intent.bookingInfo))
                }
            }
        }

        override fun executeAction(action: Action, getState: () -> FreeNegotiationsStore.State) {
            when (action) {
                is Action.GetFreeRoomsInfo -> {
                    val roomsInfoUi = mutableListOf<RoomInfoUiState>()
                    action.roomsInfo.forEach {
                        roomsInfoUi.add(RoomInfoUiState.defaultValue.copy(room = it))
                    }
                    dispatch(
                        Message.GetFreeRoomsInfo(
                            updateEventList(
                                booking = getState().booking,
                                listRooms = roomsInfoUi,
                                getState = getState
                            )
                        )
                    )
                }

                is Action.UpdateChangeEventTime -> {
                    if ((checkCurrentTime(getState().booking.eventInfo.startTime))) {
                        scope.launch {
                            val listRooms = updateTimeEvent(getState().listRooms, getState())
                            dispatch(Message.UpdateListRooms(listRooms))
                        }
                    }
                }

                is Action.ResponseError -> {
                    dispatch(Message.ResponseError)
                }

                Action.FailLoad -> dispatch(Message.ResponseError)
            }
        }

        fun updateEventList(
            booking: Booking,
            listRooms: List<RoomInfoUiState>,
            getState: () -> FreeNegotiationsStore.State
        ): List<RoomInfoUiState> {
            val updateListRooms = mutableListOf<RoomInfoUiState>()
            listRooms.forEach {
                val newRoom = it.copy(room = it.room.filter(booking.eventInfo.startTime))
                val roomState =
                    if (checkCurrentTime(booking.eventInfo.startTime)) getStateBusyRoomCurrent(
                        room = it.room,
                        state = getState()
                    )
                    else getStateBusyRoom(
                        room = it.room,
                        booking = booking
                    )
                updateListRooms.add(
                    newRoom.copy(
                        state = roomState,
                        changeEventTime = getChangeEventTime(
                            roomState = roomState,
                            startTime = booking.eventInfo.startTime,
                            room = it.room
                        )
                    )
                )

            }
            updateListRooms.sortBy { it.state.codeState }
            return updateListRooms.toList()
        }

        private fun RoomInfo.filter(date: Calendar): RoomInfo =
            copy(eventList = eventList.filter { eventInfo -> eventInfo.startTime.oneDay(date) })

        private fun Calendar.isMore(time: Calendar) = (this.timeInMillis - time.timeInMillis) >= 0


        fun getStateBusyRoomCurrent(room: RoomInfo, state: FreeNegotiationsStore.State): RoomState {
            var stateRoom: RoomState? = null
            when {
                room.currentEvent != null -> {
                    stateRoom = RoomState.BUSY
                    stateRoom.event = room.currentEvent
                    return stateRoom
                }

                room.eventList.isNotEmpty() && !checkDuration
                    (
                    startEvent = room.eventList.first().startTime,
                    newEventDuration = state.chosenDurationBooking
                ) -> {
                    stateRoom = RoomState.SOON_BUSY
                    stateRoom.event = room.eventList.first()
                    return stateRoom
                }

                else -> return RoomState.FREE
            }
        }

        fun getStateBusyRoom(room: RoomInfo, booking: Booking): RoomState {
            room.eventList.forEach {
                var stateRoom: RoomState? = null
                when {
                    (booking.eventInfo.startTime.isMore(it.startTime) &&
                            it.finishTime.isMore(booking.eventInfo.startTime)) ->
                        stateRoom = RoomState.BUSY

                    (it.startTime.isMore(booking.eventInfo.startTime) &&
                            booking.eventInfo.finishTime.isMore(it.startTime)) ->
                        stateRoom = RoomState.SOON_BUSY
                }
                if (stateRoom != null) {
                    stateRoom.event = it
                    return stateRoom
                }
            }
            return RoomState.FREE
        }

        fun getChangeEventTime(roomState: RoomState, startTime: Calendar, room: RoomInfo): Int {
            val time = if (checkCurrentTime(startTime)) Calendar.getInstance() else startTime
            return when {
                roomState == RoomState.BUSY -> getDurationRelativeCurrentTime(
                    time,
                    roomState.event!!.finishTime
                )

                roomState == RoomState.SOON_BUSY -> getDurationRelativeCurrentTime(
                    time,
                    roomState.event!!.startTime
                )

                roomState == RoomState.FREE && room.eventList.isNotEmpty() ->
                    getDurationRelativeCurrentTime(time, room.eventList.first().startTime)

                else -> -1
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
                    startEvent = roomInfo.room.eventList.first().startTime,
                    newEventDuration = state.chosenDurationBooking
                )
            ) {
                return roomInfo.copy(state = RoomState.SOON_BUSY)
            }
            if (roomInfo.changeEventTime == 0) {
                return withContext(Dispatchers.IO) {
                    val room = roomInfoUseCase(roomInfo.room.name).unbox(
                        errorHandler = {
                            it.saveData ?: RoomInfo.defaultValue
                        },
                        successHandler = {
                            it
                        }
                    )
                    val roomState = getStateBusyRoomCurrent(room, state)
                    return@withContext RoomInfoUiState(
                        room = room,
                        state = roomState,
                        changeEventTime = getChangeEventTime(
                            roomState = roomState,
                            startTime = state.booking.eventInfo.startTime,
                            room = room
                        )
                    )
                }
            } else {
                return roomInfo
            }
        }
    }

    private object ReducerImpl : Reducer<FreeNegotiationsStore.State, Message> {
        override fun FreeNegotiationsStore.State.reduce(message: Message): FreeNegotiationsStore.State =
            when (message) {
                is Message.BookRoom -> copy(
                    nameBookingRoom = message.nameRoom,
                    realDurationBooking = if (message.maxDuration > 0) {
                        chosenDurationBooking.coerceAtMost(message.maxDuration)
                    } else {
                        this.chosenDurationBooking
                    },
                    showBookingModal = true
                )

                is Message.GetFreeRoomsInfo -> copy(
                    listRooms = message.roomsInfo,
                    isLoad = false,
                    isData = true
                )

                is Message.SetBooking -> copy(
                    booking = message.bookingInfo,
                    chosenDurationBooking = getLengthEvent(
                        start = message.bookingInfo.eventInfo.startTime,
                        finish = message.bookingInfo.eventInfo.finishTime
                    ),
                    nameCurrentRoom = message.bookingInfo.nameRoom
                )

                is Message.CloseModal -> copy(showBookingModal = false)
                is Message.UpdateListRooms -> copy(
                    listRooms = message.roomsInfo
                )

                is Message.ResponseError -> copy(
                    isLoad = false,
                    error = ""
                )
            }
    }
}