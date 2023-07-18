package band.effective.office.tablet.ui.mainScreen.bookingRoomComponents.store

import band.effective.office.tablet.domain.model.EventInfo
import band.effective.office.tablet.domain.model.RoomInfo
import band.effective.office.tablet.domain.useCase.CheckBookingUseCase
import band.effective.office.tablet.domain.useCase.UpdateUseCase
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineBootstrapper
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.Calendar

class BookingStoreFactory(private val storeFactory: StoreFactory) : KoinComponent {

    val checkBookingUseCase: CheckBookingUseCase by inject()
    val updateUseCase: UpdateUseCase by inject()

    @OptIn(ExperimentalMviKotlinApi::class)
    fun create(): BookingStore =
        object : BookingStore,
            Store<BookingStore.Intent, BookingStore.State, Nothing> by storeFactory.create(
                name = "MainStore",
                initialState = BookingStore.State.default,
                bootstrapper = coroutineBootstrapper {
                    launch() {
                        val eventInfo = EventInfo.emptyEvent
                        dispatch(
                            Action.Init(
                                updateUseCase.getOrganizersList(),
                                !checkBookingUseCase(eventInfo),
                                checkBookingUseCase.busyEvent(eventInfo) ?: EventInfo.emptyEvent
                            )
                        )
                        dispatch(Action.UpdateOrganizers(updateUseCase.getOrganizersList()))
                        updateUseCase(this,
                            {
                                launch {
                                    dispatch(
                                        Action.UpdateEvents(it)
                                    )
                                }

                            },
                            { dispatch(Action.UpdateOrganizers(it)) })
                    }
                },
                executorFactory = ::ExecutorImpl,
                reducer = ReducerImpl
            ) {}

    private sealed interface Action {
        data class UpdateOrganizers(val organizers: List<String>) : Action
        data class Init(
            val organizers: List<String>,
            val isBusy: Boolean,
            val busyEvent: EventInfo
        ) : Action

        data class UpdateEvents(val newData: RoomInfo) : Action
    }

    private sealed interface Message {
        data class ChangeEvent(
            val selectDate: Calendar,
            val length: Int,
            val isBusy: Boolean,
            val busyEvent: EventInfo
        ) : Message

        data class ChangeOrganizer(val newOrganizer: String) : Message
        object BookingCurrentRoom : Message
        object BookingOtherRoom : Message
        data class UpdateOrganizers(val organizers: List<String>) : Message
        data class UpdateBusy(
            val isBusy: Boolean,
            val busyEvent: EventInfo
        ) : Message
    }

    private inner class ExecutorImpl() :
        CoroutineExecutor<BookingStore.Intent, Action, BookingStore.State, Message, Nothing>() {
        override fun executeIntent(
            intent: BookingStore.Intent,
            getState: () -> BookingStore.State
        ) {
            when (intent) {
                is BookingStore.Intent.OnBookingCurrentRoom -> dispatch(Message.BookingCurrentRoom)
                is BookingStore.Intent.OnBookingOtherRoom -> dispatch(Message.BookingOtherRoom)
                is BookingStore.Intent.OnChangeDate -> changeDate(getState(), intent.changeInDay)
                is BookingStore.Intent.OnChangeLength -> changeLength(getState(), intent.change)
                is BookingStore.Intent.OnChangeOrganizer -> dispatch(Message.ChangeOrganizer(intent.newOrganizer))
            }
        }

        override fun executeAction(action: Action, getState: () -> BookingStore.State) {
            when (action) {
                is Action.UpdateOrganizers -> dispatch(Message.UpdateOrganizers(action.organizers))
                is Action.Init -> {
                    val defaultEvent = EventInfo.emptyEvent
                    dispatch(
                        Message.ChangeEvent(
                            selectDate = defaultEvent.startTime,
                            length = 0,
                            isBusy = action.isBusy,
                            busyEvent = action.busyEvent
                        )
                    )
                }

                is Action.UpdateEvents -> checkBusy(getState())
            }
        }

        fun changeDate(state: BookingStore.State, changeDay: Int) = scope.launch() {
            val event = state.copy(selectDate = state.selectDate.dayUpdate(changeDay)).toEvent()
            dispatch(
                Message.ChangeEvent(
                    event.startTime,
                    state.length,
                    !checkBookingUseCase(event),
                    checkBookingUseCase.busyEvent(event) ?: EventInfo.emptyEvent
                )
            )
        }

        fun changeLength(state: BookingStore.State, change: Int) = scope.launch() {
            val event = state.copy(length = state.length + change).toEvent()
            dispatch(
                Message.ChangeEvent(
                    event.startTime,
                    state.length + change,
                    !checkBookingUseCase(event),
                    checkBookingUseCase.busyEvent(event) ?: EventInfo.emptyEvent
                )
            )
        }

        fun checkBusy(state: BookingStore.State) = scope.launch {
            dispatch(
                Message.UpdateBusy(
                    !checkBookingUseCase(state.toEvent()),
                    checkBookingUseCase.busyEvent(state.toEvent()) ?: EventInfo.emptyEvent
                )
            )
        }
    }

    private fun BookingStore.State.toEvent(): EventInfo {
        val finishDate = selectDate.clone() as Calendar
        finishDate.add(Calendar.MINUTE, length)
        return EventInfo(
            startTime = selectDate.clone() as Calendar,
            finishTime = finishDate,
            organizer = organizer
        )
    }

    private object ReducerImpl : Reducer<BookingStore.State, Message> {
        override fun BookingStore.State.reduce(msg: Message): BookingStore.State =
            when (msg) {
                is Message.BookingCurrentRoom -> copy()
                is Message.BookingOtherRoom -> copy()
                is Message.ChangeEvent -> copy(
                    selectDate = msg.selectDate,
                    length = msg.length,
                    isBusy = msg.isBusy,
                    busyEvent = msg.busyEvent
                )

                is Message.ChangeOrganizer -> copy(organizer = msg.newOrganizer)
                is Message.UpdateOrganizers -> copy(organizers = msg.organizers)
                is Message.UpdateBusy -> copy(isBusy = msg.isBusy, busyEvent = msg.busyEvent)
            }

    }

    private fun Calendar.dayUpdate(changeDay: Int): Calendar {
        val result = clone() as Calendar
        result.add(Calendar.DAY_OF_MONTH, changeDay)
        return result
    }
}
