package band.effective.office.tablet.ui.mainScreen.slotComponent.store

import android.os.Build
import androidx.annotation.RequiresApi
import band.effective.office.network.model.Either
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

class SlotStoreFactory(private val storeFactory: StoreFactory, private val roomName: String) :
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
            when (val roomInfoEither = updateUseCase.getRoomInfo(roomName)) {
                is Either.Error -> TODO()
                is Either.Success -> {
                    val roomInfo = roomInfoEither.data
                    val now = GregorianCalendar()
                    val endDay = GregorianCalendar().apply {
                        add(Calendar.DAY_OF_YEAR, 1)
                        set(Calendar.HOUR_OF_DAY, 0)
                    }
                    val slots = slotUseCase.getSlots(
                        start = now,
                        finish = endDay,
                        minSlotDur = 15,
                        events = roomInfo.eventList
                    )
                    dispatch(Action.UpdateSlots(slots))
                }
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