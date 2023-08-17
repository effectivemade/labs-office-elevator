package band.effective.office.tablet.ui.mainScreen.roomInfoComponents.store

import band.effective.office.tablet.domain.CurrentEventController
import band.effective.office.tablet.domain.model.EventInfo
import band.effective.office.tablet.domain.model.RoomInfo
import band.effective.office.tablet.domain.useCase.CheckSettingsUseCase
import band.effective.office.tablet.domain.useCase.UpdateUseCase
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
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.Calendar
import java.util.GregorianCalendar

class RoomInfoFactory(private val storeFactory: StoreFactory) : KoinComponent {

    private val updateUseCase: UpdateUseCase by inject()
    private val currentEventController: CurrentEventController by inject()
    private val checkSettingsUseCase: CheckSettingsUseCase by inject()

    @OptIn(ExperimentalMviKotlinApi::class)
    fun create(): RoomInfoStore =
        object : RoomInfoStore,
            Store<RoomInfoStore.Intent, RoomInfoStore.State, Nothing> by storeFactory.create(
                name = "RoomInfoStore",
                initialState = RoomInfoStore.State.defaultState,
                bootstrapper = coroutineBootstrapper {
                    launch {
                        dispatch(
                            Action.UpdateNameRoom(
                                checkSettingsUseCase()
                            )
                        )
                        dispatch(
                            Action.UpdateRoomInfo(
                                updateUseCase.getRoomInfo(checkSettingsUseCase()).unbox(
                                    errorHandler = {
                                        dispatch(Action.OnResponse(false))
                                        it.saveData ?: RoomInfo.defaultValue
                                    },
                                    successHandler = {
                                        dispatch(Action.OnResponse(true))
                                        it
                                    }
                                )
                            )
                        )
                    }
                    launch {
                        currentEventController.timeToUpdate.collect {
                            dispatch(
                                Action.UpdateChangeEventTime(
                                    (it / 60000).toInt()
                                )
                            )
                        }
                    }
                    launch() {
                        dispatch(
                            Action.UpdateRoomInfo(
                                updateUseCase.getRoomInfo(checkSettingsUseCase())
                                    .unbox(
                                        errorHandler = {
                                            dispatch(Action.OnResponse(false))
                                            it.saveData ?: RoomInfo.defaultValue
                                        },
                                        successHandler = {
                                            dispatch(Action.OnResponse(true))
                                            it
                                        }
                                    )
                            )
                        )
                        updateUseCase(
                            scope = this,
                            roomUpdateHandler = { roomInfo ->
                                launch(Dispatchers.Main.immediate) {
                                    dispatch(Action.UpdateRoomInfo(roomInfo.unbox(
                                        errorHandler = {
                                            dispatch(Action.OnResponse(false))
                                            it.saveData ?: RoomInfo.defaultValue
                                        },
                                        successHandler = {
                                            dispatch(Action.OnResponse(true))
                                            it
                                        }
                                    )))
                                }

                            },
                            organizerUpdateHandler = {})
                    }
                },
                executorFactory = ::ExecutorImpl,
                reducer = ReducerImpl
            ) {}

    private sealed interface Action {
        data class UpdateRoomInfo(val roomInfo: RoomInfo) : Action
        data class UpdateChangeEventTime(val newValue: Int) : Action
        data class UpdateNameRoom(val nameRoom: String): Action
        data class OnResponse(val isSuccess: Boolean) : Action
    }

    private sealed interface Message {
        data class UpdateRoomInfo(val roomInfo: RoomInfo, val nextEvent: EventInfo) : Message
        data class UpdateChangeEventTime(val newValue: Int) : Message
        data class UpdateDate(val newValue: Calendar, val eventList: List<EventInfo>) : Message
        data class UpdateNameRoom(val nameRoom: String): Message
        data class OnResponse(val isSuccess: Boolean) : Message
    }

    private inner class ExecutorImpl() :
        CoroutineExecutor<RoomInfoStore.Intent, Action, RoomInfoStore.State, Message, Nothing>() {
        override fun executeIntent(
            intent: RoomInfoStore.Intent,
            getState: () -> RoomInfoStore.State
        ) {
            when (intent) {
                is RoomInfoStore.Intent.OnChangeSelectDate -> updateDate(
                    newDate = intent.newValue,
                    nameRoom = getState().roomInfo.name
                )
                else -> {}
            }
        }

        private fun RoomInfo.filter(date: Calendar): RoomInfo =
            copy(eventList = eventList.filter { eventInfo -> eventInfo.startTime.oneDay(date) })

        override fun executeAction(action: Action, getState: () -> RoomInfoStore.State) {
            when (action) {
                is Action.UpdateRoomInfo -> with(action.roomInfo.filter(getState().selectDate)) {
                    dispatch(
                        Message.UpdateRoomInfo(
                            roomInfo = this,
                            nextEvent = eventList.firstOrNull { it.startTime > GregorianCalendar() }
                                ?: EventInfo.emptyEvent
                        )
                    )
                }

                is Action.UpdateChangeEventTime -> dispatch(Message.UpdateChangeEventTime(action.newValue))
                is Action.OnResponse -> dispatch(Message.OnResponse(action.isSuccess))
                is Action.UpdateNameRoom -> dispatch(Message.UpdateNameRoom(action.nameRoom))
            }
        }
        private fun updateDate(newDate: Calendar, nameRoom: String) = scope.launch {
            dispatch(
                Message.UpdateDate(
                    newValue = newDate,
                    eventList = updateUseCase.getRoomInfo(nameRoom)
                        .unbox(
                            errorHandler = {
                                dispatch(Message.OnResponse(false))
                                it.saveData ?: RoomInfo.defaultValue
                            },
                            successHandler = {
                                dispatch(Message.OnResponse(true))
                                it
                            }
                        )
                        .filter(newDate).eventList
                )
            )
        }
    }

    private object ReducerImpl : Reducer<RoomInfoStore.State, Message> {
        override fun RoomInfoStore.State.reduce(message: Message): RoomInfoStore.State =
            when (message) {
                is Message.UpdateChangeEventTime -> copy(changeEventTime = message.newValue)
                is Message.UpdateDate -> copy(
                    selectDate = message.newValue,
                    roomInfo = roomInfo.copy(eventList = message.eventList)
                )

                is Message.UpdateRoomInfo -> copy(
                    roomInfo = message.roomInfo,
                    nextEvent = message.nextEvent
                )

                is Message.UpdateNameRoom -> copy(
                    roomInfo = roomInfo.copy(name = message.nameRoom)
                )

                is Message.OnResponse -> copy(isError = !message.isSuccess)
            }
    }
}