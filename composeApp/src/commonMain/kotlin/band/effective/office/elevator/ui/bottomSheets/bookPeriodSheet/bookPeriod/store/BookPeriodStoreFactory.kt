package band.effective.office.elevator.ui.bottomSheets.bookPeriodSheet.bookPeriod.store

import band.effective.office.elevator.domain.models.BookingPeriod
import band.effective.office.elevator.domain.models.TypeEndPeriodBooking
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import dev.icerock.moko.resources.StringResource
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

class BookPeriodStoreFactory(
    private val storeFactory: StoreFactory,
    private val initState: BookPeriodStore.State
) {

    fun create(): BookPeriodStore =
        object : BookPeriodStore,
            Store<BookPeriodStore.Intent, BookPeriodStore.State, Nothing> by storeFactory.create(
                name = "BookPeriodStore",
                initialState = initState,
                executorFactory = ::ExecutorImpl,
                reducer = ReducerImpl
            ) {}

    private sealed interface Message {
        data class UpdateStartTime(val newValue: LocalTime) : Message
        data class UpdateFinishTime(val newValue: LocalTime) : Message
        data class UpdateSwitchAllDay(val newValue: Boolean) : Message
        data class UpdateStartDate(val newValue: LocalDate) : Message
        data class UpdateFinishDate(val newValue: LocalDate) : Message
        data class UpdateDayOfEndPeriod(val newValue: LocalDate) : Message
        data class UpdatePeriod(val newValue: BookingPeriod) : Message
        data class UpdateEndType(val newValue: TypeEndPeriodBooking) : Message
        data class UpdateRepeatBooking(val newValue: StringResource) : Message
    }

    private inner class ExecutorImpl :
        CoroutineExecutor<BookPeriodStore.Intent, Nothing, BookPeriodStore.State, Message, Nothing>() {
        override fun executeIntent(
            intent: BookPeriodStore.Intent,
            getState: () -> BookPeriodStore.State
        ) {
            when (intent) {
                is BookPeriodStore.Intent.InputTime -> {
                    if (intent.isStart) {
                        dispatch(Message.UpdateStartTime(intent.time))
                    } else {
                        dispatch(Message.UpdateFinishTime(intent.time))
                    }
                }

                BookPeriodStore.Intent.OnSwitchAllDay -> {
                    dispatch(Message.UpdateSwitchAllDay(!getState().switchChecked))
                }

                is BookPeriodStore.Intent.InputDate -> {
                    dispatch(Message.UpdateStartDate(intent.selectDates.first()))
                    dispatch(Message.UpdateFinishDate(intent.selectDates.last()))
                }

                is BookPeriodStore.Intent.InputDayOfEndPeriod -> {
                    dispatch(Message.UpdateDayOfEndPeriod(intent.dateOfEndPeriod))
                }

                is BookPeriodStore.Intent.InputPeriod -> {
                    dispatch(Message.UpdatePeriod(intent.bookingPeriod))
                    //dispatch(Message.UpdateRepeatBooking(intent.repeatBooking))
                }

                is BookPeriodStore.Intent.InputEndType -> {
                    dispatch(Message.UpdateEndType(intent.endPeriodBookingType))
                }
            }
        }
    }

    private object ReducerImpl : Reducer<BookPeriodStore.State, Message> {
        override fun BookPeriodStore.State.reduce(msg: Message): BookPeriodStore.State =
            when (msg) {
                is Message.UpdateStartDate -> copy(startDate = msg.newValue)
                is Message.UpdateDayOfEndPeriod -> copy(dateOfEndPeriod = msg.newValue)
                is Message.UpdateFinishTime -> copy(finishTime = msg.newValue)
                is Message.UpdatePeriod -> copy(bookingPeriod = msg.newValue)
                is Message.UpdateStartTime -> copy(startTime = msg.newValue)
                is Message.UpdateSwitchAllDay -> copy(switchChecked = msg.newValue)
                is Message.UpdateFinishDate -> copy(finishDate = msg.newValue)
                is Message.UpdateEndType -> copy(endPeriodBookingType = msg.newValue)
                is Message.UpdateRepeatBooking -> copy(repeatBooking = msg.newValue)
            }
    }
}