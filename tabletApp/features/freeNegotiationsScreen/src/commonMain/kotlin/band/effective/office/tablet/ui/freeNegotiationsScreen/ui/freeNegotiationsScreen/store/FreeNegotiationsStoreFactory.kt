package band.effective.office.tablet.ui.freeNegotiationsScreen.ui.freeNegotiationsScreen.store

import band.effective.office.tablet.domain.model.Booking
import band.effective.office.tablet.domain.model.RoomInfo
import band.effective.office.tablet.ui.freeNegotiationsScreen.domain.MockListRooms
import band.effective.office.tablet.ui.freeNegotiationsScreen.ui.freeNegotiationsScreen.roomUiState.RoomInfoUiState
import band.effective.office.tablet.ui.freeNegotiationsScreen.ui.freeNegotiationsScreen.roomUiState.RoomState
import band.effective.office.tablet.ui.freeNegotiationsScreen.ui.freeNegotiationsScreen.timer.Timer
import band.effective.office.tablet.ui.freeNegotiationsScreen.ui.freeNegotiationsScreen.uiComponents.checkDuration
import band.effective.office.tablet.ui.freeNegotiationsScreen.ui.freeNegotiationsScreen.uiComponents.getDurationRelativeCurrentTime
import band.effective.office.tablet.ui.selectRoomScreen.uiComponents.getLengthEvent
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineBootstrapper
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import java.util.Calendar

class FreeNegotiationsStoreFactory(private val storeFactory: StoreFactory) : KoinComponent {

    private val timer = Timer()

    @OptIn(ExperimentalMviKotlinApi::class)
    fun create(): FreeNegotiationsStore =
        object : FreeNegotiationsStore,
            Store<FreeNegotiationsStore.Intent, FreeNegotiationsStore.State, Nothing> by storeFactory.create(
                name = "FreeNegotiationsStore",
                initialState = FreeNegotiationsStore.State.defaultState,
                bootstrapper = coroutineBootstrapper {
                    launch() {
                        dispatch(Action.GetFreeRoomsInfo(MockListRooms.listRooms))
                        timer.subscribe {
                            dispatch(
                                Action.UpdateChangeEventTime
                            )
                        }
                        timer.startTimer()
                    }
                },
                executorFactory = ::ExecutorImpl,
                reducer = ReducerImpl
            ) {}

    private sealed interface Action {
        data class GetFreeRoomsInfo(val roomsInfo: List<RoomInfo>) : Action
        object UpdateChangeEventTime : Action
    }

    private sealed interface Message {
        data class GetFreeRoomsInfo(val roomsInfo: List<RoomInfoUiState>) : Message
        data class SetBooking(val bookingInfo: Booking) : Message
        data class BookRoom(val nameRoom: String, val maxDuration: Int) : Message
        object CloseModal : Message
        object UpdateChangeEventTime : Message

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
                    dispatch(Message.UpdateChangeEventTime)
                }
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
                    listRooms = updateTimeEvent(this.listRooms)
                )
            }

        fun updateTimeEvent(
            listRooms: List<RoomInfoUiState>
        ): List<RoomInfoUiState> {
            val newListRooms = mutableListOf<RoomInfoUiState>()
            listRooms.forEach {
                if (it.changeEventTime != -1) {
                    val roomNewTime = it.copy(changeEventTime = it.changeEventTime - 1)
                    newListRooms.add(roomNewTime)
                } else {
                    newListRooms.add(it)
                }
            }
            return newListRooms.toList()
        }

        fun updateState(
            roomInfo: RoomInfoUiState
        ): RoomInfoUiState {
            when {
                (roomInfo.state == RoomState.BUSY && roomInfo.changeEventTime == 0) ->
                {
                   //TODO(take info room from back)
                }
            }
            return roomInfo
        }
    }
}