package band.effective.office.tablet.ui.mainScreen.slotComponent.store

import android.os.Build
import androidx.annotation.RequiresApi
import band.effective.office.network.model.Either
import band.effective.office.tablet.domain.model.ErrorWithData
import band.effective.office.tablet.domain.model.EventInfo
import band.effective.office.tablet.domain.model.Organizer
import band.effective.office.tablet.domain.model.RoomInfo
import band.effective.office.tablet.domain.model.Slot
import band.effective.office.tablet.domain.useCase.SlotUseCase
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
import java.util.GregorianCalendar

class SlotStoreFactory(
    private val storeFactory: StoreFactory,
    private val roomName: String,
    private val openBookingDialog: (event: EventInfo, room: String) -> Unit
) :
    KoinComponent {
    val slotUseCase: SlotUseCase by inject()
    val updateUseCase: UpdateUseCase by inject()

    @RequiresApi(Build.VERSION_CODES.N)
    fun create(): SlotStore = object : SlotStore,
        Store<SlotStore.Intent, SlotStore.State, Nothing> by storeFactory.create(
            name = "SlotStore",
            bootstrapper = bootstrapper(),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl,
            initialState = SlotStore.State.initValue
        ) {}

    @RequiresApi(Build.VERSION_CODES.N)
    @OptIn(ExperimentalMviKotlinApi::class)
    private fun bootstrapper() = coroutineBootstrapper<Action> {
        launch {
            dispatch(Action.UpdateSlots(getSlots(updateUseCase.getRoomInfo(roomName))))
        }
        updateUseCase(
            room = roomName,
            scope = this,
            roomUpdateHandler = { dispatch(Action.UpdateSlots(getSlots(it))) },
            organizerUpdateHandler = {})
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getSlots(either: Either<ErrorWithData<RoomInfo>, RoomInfo>): List<Slot> {
        return when (either) {
            is Either.Error -> listOf()
            is Either.Success -> {
                val roomInfo = either.data
                val now = GregorianCalendar()
                val endDay = GregorianCalendar().apply {
                    add(Calendar.DAY_OF_YEAR, 1)
                    set(Calendar.HOUR_OF_DAY, 0)
                }
                slotUseCase.getSlots(
                    start = now,
                    finish = endDay,
                    minSlotDur = 15,
                    events = roomInfo.eventList
                )
            }
        }
    }

    private inner class ExecutorImpl() :
        CoroutineExecutor<SlotStore.Intent, Action, SlotStore.State, Message, Nothing>() {
        override fun executeAction(action: Action, getState: () -> SlotStore.State) {
            when (action) {
                is Action.UpdateSlots -> dispatch(Message.UpdateSlots(action.slots))
            }
        }

        @RequiresApi(Build.VERSION_CODES.N)
        override fun executeIntent(intent: SlotStore.Intent, getState: () -> SlotStore.State) {
            when (intent) {
                is SlotStore.Intent.ClickOnSlot -> intent.slot.execute()
                SlotStore.Intent.UpdateRequest -> updateSlot()
            }
        }

        @RequiresApi(Build.VERSION_CODES.N)
        private fun updateSlot() {
            scope.launch {
                dispatch(Message.UpdateSlots(getSlots(updateUseCase.getRoomInfo(
                    nameRoom = roomName,
                    refresh = true
                ))))
            }
        }

        private fun Slot.execute() = when (this) {
            is Slot.EmptySlot -> executeFreeSlot(this)
            is Slot.EventSlot -> executeEventSlot(this)
        }

        private fun executeFreeSlot(slot: Slot.EmptySlot) {
            openBookingDialog(slot.Event(), roomName)
        }

        private fun executeEventSlot(slot: Slot.EventSlot) {
            openBookingDialog(slot.eventInfo, roomName)
        }

        private fun Slot.EmptySlot.Event(): EventInfo =
            EventInfo(
                startTime = start,
                finishTime = finish.run {
                    if (minuteDuration() <= 30)
                        this
                    else (start.clone() as Calendar).apply {
                        add(Calendar.MINUTE, 30)
                    }
                },
                organizer = Organizer.default,
                id = ""
            )//TODO id

    }

    private object ReducerImpl : Reducer<SlotStore.State, Message> {
        override fun SlotStore.State.reduce(msg: Message): SlotStore.State =
            when (msg) {
                is Message.UpdateSlots -> copy(slots = msg.slots)
            }
    }

    private sealed interface Action {
        data class UpdateSlots(val slots: List<Slot>) : Action
    }

    private sealed interface Message {
        data class UpdateSlots(val slots: List<Slot>) : Message
    }
}

