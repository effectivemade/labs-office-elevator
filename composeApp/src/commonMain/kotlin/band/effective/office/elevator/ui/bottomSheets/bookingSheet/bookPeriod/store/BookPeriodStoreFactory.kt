package band.effective.office.elevator.ui.bottomSheets.bookingSheet.bookPeriod.store

import band.effective.office.elevator.MainRes
import band.effective.office.elevator.domain.models.BookingPeriod
import band.effective.office.elevator.domain.models.TypeEndPeriodBooking
import band.effective.office.elevator.utils.getCurrentDate
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.commandiron.wheel_picker_compose.utils.getCurrentTime
import dev.icerock.moko.resources.StringResource
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.minus

class BookPeriodStoreFactory(
    private val storeFactory: StoreFactory,
    private val initState: BookPeriodStore.State,
) {

    fun create(): BookPeriodStore =
        object : BookPeriodStore,
            Store<BookPeriodStore.Intent, BookPeriodStore.State, BookPeriodStore.Label> by storeFactory.create(
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
        CoroutineExecutor<BookPeriodStore.Intent, Nothing, BookPeriodStore.State, Message, BookPeriodStore.Label>() {
        override fun executeIntent(
            intent: BookPeriodStore.Intent,
            getState: () -> BookPeriodStore.State
        ) {
            when (intent) {
                is BookPeriodStore.Intent.InputTime ->
                    setTime(
                        newTime = intent.time,
                        prevStartTime = getState().startTime,
                        startDate = getState().startDate,
                        isStart = intent.isStart
                    )

                BookPeriodStore.Intent.OnSwitchAllDay ->
                    setTimeOnAllDay(
                        startDate = getState().startDate,
                        finishDate = getState().finishDate,
                        switchChecked = getState().switchChecked
                    )

                is BookPeriodStore.Intent.InputDate ->
                    setDate(
                        dates = intent.selectDates,
                        prevBookingPeriod = getState().bookingPeriod
                    )

                is BookPeriodStore.Intent.InputDayOfEndPeriod -> {
                    dispatch(Message.UpdateDayOfEndPeriod(intent.dateOfEndPeriod))
                }

                is BookPeriodStore.Intent.InputPeriod -> {
                    dispatch(Message.UpdatePeriod(intent.bookingPeriod))
                }

                is BookPeriodStore.Intent.InputEndType -> {
                    dispatch(Message.UpdateEndType(intent.endPeriodBookingType))
                }

                is BookPeriodStore.Intent.OnChangeCustomFrequency ->
                    setCustomFrequency(
                        bookingPeriod = intent.bookingPeriod,
                        typeEndPeriodBooking = intent.typeEndPeriodBooking,
                        startDate = getState().startDate
                    )

                is BookPeriodStore.Intent.OnChangeTemplateFrequency ->
                    setTemplateFrequency(bookingPeriod = intent.bookingPeriod)
            }
        }

        private fun setFrequencyTitle(bookingPeriod: BookingPeriod) {
            val title = when (bookingPeriod) {
                is BookingPeriod.EveryWorkDay -> MainRes.strings.every_work_day
                is BookingPeriod.Month -> MainRes.strings.every_month
                is BookingPeriod.NoPeriod -> MainRes.strings.booking_not_repeat
                is BookingPeriod.Week -> MainRes.strings.every_week
                is BookingPeriod.Year -> MainRes.strings.every_month
                is BookingPeriod.Another -> MainRes.strings.another
                BookingPeriod.Day -> MainRes.strings.another
            }
            dispatch(Message.UpdateRepeatBooking(title))
        }

        private fun setCustomFrequency(
            bookingPeriod: BookingPeriod,
            typeEndPeriodBooking: TypeEndPeriodBooking,
            startDate: LocalDate,
        ) {
            val typeOfEnd = when (typeEndPeriodBooking) {
                is TypeEndPeriodBooking.DatePeriodEnd -> {
                    // TODO: hen backend fix until date, it`s can be removed
                    val dateRange = typeEndPeriodBooking.date - startDate
                    val timeUnit = when (bookingPeriod) {
                        is BookingPeriod.Week -> 7
                        is BookingPeriod.EveryWorkDay -> 7
                        is BookingPeriod.Year -> 365
                        is BookingPeriod.Month -> 30
                        BookingPeriod.Another -> 1
                        BookingPeriod.Day -> 1
                        BookingPeriod.NoPeriod -> 1
                    }
                    TypeEndPeriodBooking.CountRepeat(dateRange.days / timeUnit + 1)
                }
                else -> typeEndPeriodBooking
            }
            dispatch(Message.UpdatePeriod(bookingPeriod))
            dispatch(Message.UpdateEndType(typeOfEnd))
            setFrequencyTitle(bookingPeriod)
        }

        private fun setTemplateFrequency(bookingPeriod: BookingPeriod) {
            dispatch(Message.UpdatePeriod(newValue = bookingPeriod))
            dispatch(Message.UpdateEndType(TypeEndPeriodBooking.CountRepeat(10))) // TODO(Artem Gruzdev) backend should fix this
            setFrequencyTitle(bookingPeriod)
        }

        private fun setTime(
            newTime: LocalTime,
            prevStartTime: LocalTime,
            startDate: LocalDate,
            isStart: Boolean
        ) {
            val currentTime = getCurrentTime()
            val currentDate = getCurrentDate()
            when(isStart) {
                true -> {
                    if (currentDate == startDate) {
                        if (newTime >= currentTime)
                            dispatch(Message.UpdateStartTime(newTime))
                        else
                            publish(BookPeriodStore.Label.ShowToast("Некорретное время"))
                    } else
                        dispatch(Message.UpdateStartTime(newTime))
                }
                false -> {
                    if (newTime > prevStartTime)
                        dispatch(Message.UpdateFinishTime(newTime))
                    else
                        publish(BookPeriodStore.Label.ShowToast("Некорретное время"))
                }
            }
        }

        // NOTE: if user select date range, then function set bookingPeriod and typeEnd
        private fun setDate(
            dates: List<LocalDate>,
            prevBookingPeriod: BookingPeriod
        ) {
            if (dates.isEmpty()) return

            val startDate = dates.first()
            val endDate = dates.last()
            val datePeriod = endDate - startDate

            val currentDate = getCurrentDate()

            if (startDate >= currentDate) {
                dispatch(Message.UpdateStartDate(newValue = startDate))
            } else {
                publish(BookPeriodStore.Label.ShowToast("Некорректная дата"))
            }
            dispatch(
                Message.UpdateFinishDate(newValue = endDate)
            )
            // user select date range
            if (startDate != endDate) {
                dispatch(Message.UpdatePeriod(BookingPeriod.Day))
                dispatch(
                    Message.UpdateEndType(
                        TypeEndPeriodBooking.CountRepeat(
                            datePeriod.days + 1
                        )
                    )
                )
            }
            else if (prevBookingPeriod is BookingPeriod.Day) {
                dispatch(Message.UpdatePeriod(BookingPeriod.NoPeriod))
                dispatch(Message.UpdateEndType(TypeEndPeriodBooking.CountRepeat(1)))
            }
        }

        private fun setTimeOnAllDay(
            startDate: LocalDate,
            finishDate: LocalDate,
            switchChecked: Boolean
        ) {
            if (startDate == finishDate) {
                dispatch(Message.UpdateSwitchAllDay(!switchChecked))
                dispatch(Message.UpdateStartTime(newValue = LocalTime(hour = 8, minute = 0)))
                dispatch(Message.UpdateFinishTime(newValue = LocalTime(hour = 20, minute = 0)))
            } else {
                publish(BookPeriodStore.Label.ShowToast("У вас выбраны разные даты"))
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