package band.effective.office.tablet.ui.bookingComponents.pickerDateTime

import band.effective.office.tablet.domain.model.EventInfo
import band.effective.office.tablet.domain.model.RoomInfo
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineBootstrapper
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import java.util.Calendar
import java.util.GregorianCalendar
import kotlin.math.absoluteValue

class DateTimePickerStoreFactory(private val storeFactory: StoreFactory) : KoinComponent {
    @OptIn(ExperimentalMviKotlinApi::class)
    fun create(): DateTimePickerStore =
        object : DateTimePickerStore,
            Store<DateTimePickerStore.Intent, DateTimePickerStore.State, Nothing> by storeFactory.create(
                name = "MainStore",
                initialState = DateTimePickerStore.State.default,
                bootstrapper = coroutineBootstrapper { },
                executorFactory = ::ExecutorImpl,
                reducer = ReducerImpl
            ) {}

    private sealed interface Action {
        object Init: Action
        data class UpdateEvents(val newData: RoomInfo) : Action
        object UpdateSelectTime : Action
    }

    private sealed interface Message {
        data class ChangeEvent(
            val selectDate: Calendar,
            val isSelectCurrentTime: Boolean
        ) : Message

        object Reset : Message
        object UpdateTime : Message
    }


    private inner class ExecutorImpl() :
        CoroutineExecutor<DateTimePickerStore.Intent, Action, DateTimePickerStore.State, Message, Nothing>() {
        override fun executeIntent(
            intent: DateTimePickerStore.Intent,
            getState: () -> DateTimePickerStore.State
        ) {
            when (intent) {
                is DateTimePickerStore.Intent.OnSetDate -> {
                    setNewDate(getState(), intent.changedDay, intent.changedMonth, intent.changedYear, intent.changedHour, intent.changedMinute)
                }

                is DateTimePickerStore.Intent.CloseModal -> intent.close?.invoke()
                else -> {}
            }
        }

        var resetTimer: Job? = null

        fun reset() {
            resetTimer?.cancel()
            resetTimer = scope.launch {
                delay(60000)
                dispatch(Message.Reset)
            }
        }

        override fun executeAction(action: Action, getState: () -> DateTimePickerStore.State) {
            when (action) {
                is Action.Init -> {
                    val defaultEvent = EventInfo.emptyEvent
                    dispatch(
                        Message.ChangeEvent(
                            selectDate = defaultEvent.startTime,
                            isSelectCurrentTime = true
                        )
                    )
                }

                is Action.UpdateEvents -> reset()
                Action.UpdateSelectTime -> dispatch(Message.UpdateTime)
            }
        }

        fun setNewDate(state: DateTimePickerStore.State, changeDay: Int, changeMonth: Int, changeYear: Int, changeHour: Int, changeMinute: Int) = scope.launch() {
            val newDate = (state.selectDate.clone() as Calendar).apply {
                set(
                    changeYear,
                    changeMonth,
                    changeDay,
                    changeHour,
                    changeMinute
                )
            }
            dispatch(
                Message.ChangeEvent(
                    selectDate = newDate,
                    isSelectCurrentTime = newDate.isNow()
                )
            )
            reset()
        }
    }

    private object ReducerImpl : Reducer<DateTimePickerStore.State, Message> {
        override fun DateTimePickerStore.State.reduce(msg: Message): DateTimePickerStore.State =
            when (msg) {
                is Message.ChangeEvent -> copy(
                    selectDate = msg.selectDate,
                    isSelectCurrentTime = msg.isSelectCurrentTime,
                )

                is Message.Reset -> reset()
                is Message.UpdateTime -> copy(currentDate = GregorianCalendar())
            }

        fun DateTimePickerStore.State.reset() = copy(
            selectDate = GregorianCalendar(),
            isSelectCurrentTime = true,
        )

    }

}

private fun Calendar.isNow(): Boolean {
    val difference = (this.time.time - GregorianCalendar().time.time).absoluteValue
    return difference <= 60000
}