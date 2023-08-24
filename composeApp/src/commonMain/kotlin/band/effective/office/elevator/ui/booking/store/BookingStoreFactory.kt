package band.effective.office.elevator.ui.booking.store

import band.effective.office.elevator.MainRes
import band.effective.office.elevator.domain.entity.BookingInteractor
import band.effective.office.elevator.domain.models.BookingInfo
import band.effective.office.elevator.domain.models.BookingPeriodUI
import band.effective.office.elevator.domain.models.CreatingBookModel
import band.effective.office.elevator.domain.models.TypeEndPeriodBooking
import band.effective.office.elevator.ui.booking.models.Frequency
import band.effective.office.elevator.ui.booking.models.WorkSpaceType
import band.effective.office.elevator.ui.booking.models.WorkSpaceUI
import band.effective.office.elevator.ui.booking.models.WorkSpaceZone
import band.effective.office.elevator.ui.models.TypesList
import band.effective.office.elevator.utils.getCurrentDate
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineBootstrapper
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.atTime
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import dev.icerock.moko.resources.StringResource

class BookingStoreFactory(private val storeFactory: StoreFactory) : KoinComponent {

    private val bookingInteractor: BookingInteractor by inject()

    @OptIn(ExperimentalMviKotlinApi::class)
    fun create(): BookingStore =
        object : BookingStore,
            Store<BookingStore.Intent, BookingStore.State, BookingStore.Label> by storeFactory.create(
                name = "BookingStore",
                initialState = BookingStore.State.initState,
                bootstrapper = coroutineBootstrapper {
                    launch {
                        dispatch(Action.InitWorkSpaces)
                    }
                },
                executorFactory = ::ExecutorImpl,
                reducer = ReducerImpl
            ) {}

    private sealed interface Msg {
        data class BeginningBookingTime(val time: LocalTime) : Msg
        data class BeginningBookingDate(val date: LocalDate) : Msg
        data class EndBookingTime(val time: LocalTime) : Msg
        data class EndBookingDate(val date: LocalDate) : Msg
        data class SelectedTypeList(val type: TypesList) : Msg
        data class DateBooking(val date: LocalDate) : Msg
        data class TimeBooking(val time: LocalTime) : Msg

        data class ChangeSelectedWorkSpacesZone(val workSpacesZone: List<WorkSpaceZone>) : Msg

        data class ChangeWorkSpacesUI(val workSpacesUI: List<WorkSpaceUI>) : Msg

        data class WholeDay(val wholeDay: Boolean) : Msg
        data class IsStartTimePicked(val isStart: Boolean) : Msg
        data class ChangeFrequency(val frequency: Frequency) : Msg
        data class ChangeBookingRepeat(val bookingRepeat: StringResource) : Msg
        data class ChangeBookingPeriod(val bookingPeriodUI: BookingPeriodUI) : Msg
        data class ChangeWorkingUI(val bookingInfo: BookingInfo) : Msg
    }

    private sealed interface Action {
        object InitWorkSpaces : Action
    }

    private inner class ExecutorImpl :
        CoroutineExecutor<BookingStore.Intent, Action, BookingStore.State, Msg, BookingStore.Label>() {
        override fun executeIntent(
            intent: BookingStore.Intent,
            getState: () -> BookingStore.State
        ) {
            when (intent) {
                is BookingStore.Intent.ShowPlace -> dispatch(
                    Msg.SelectedTypeList(
                        type = TypesList(
                            name = MainRes.strings.app_name,
                            icon = MainRes.images.table_icon,
                            type = WorkSpaceType.WORK_PLACE
                        )
                    )
                )

                is BookingStore.Intent.OpenChooseZone -> {
                    scope.launch {
                        publish(BookingStore.Label.OpenChooseZone)
                    }
                }

                is BookingStore.Intent.CloseChooseZone -> {
                    scope.launch {
                        publish(BookingStore.Label.CloseChooseZone)
                    }
                }

                is BookingStore.Intent.OpenBookPeriod -> {
                    scope.launch {
                        publish(BookingStore.Label.OpenBookPeriod)
                    }
                }

                is BookingStore.Intent.CloseBookPeriod -> {
                    scope.launch {
                        publish(BookingStore.Label.CloseBookPeriod)
                    }
                }

                is BookingStore.Intent.OpenRepeatDialog -> {
                    scope.launch {
                        publish(BookingStore.Label.OpenRepeatDialog)
                    }
                }

                is BookingStore.Intent.OpenBookAccept -> {
                    scope.launch {
                        publish(BookingStore.Label.OpenBookAccept(value = intent.value))
                        with(intent.value) {
                            dispatch(
                                Msg.ChangeWorkingUI(
                                    bookingInfo = BookingInfo(
                                        id = workSpaceId,
                                        ownerId = "",
                                        seatName = workSpaceName,
                                        dateOfEnd = LocalDateTime(
                                            date = getState().selectedStartDate,
                                            time = getState().selectedFinishTime
                                        ),
                                        dateOfStart = LocalDateTime(
                                            date = getState().selectedStartDate,
                                            time = getState().selectedStartTime
                                        )
                                    )
                                )
                            )
                        }
                    }
                }

                is BookingStore.Intent.CloseBookAccept -> {
                    scope.launch {
                        publish(BookingStore.Label.CloseBookAccept)
                    }
                }

                is BookingStore.Intent.OpenCalendar -> {
                    scope.launch {
                        publish(BookingStore.Label.OpenCalendar)
                    }
                }

                is BookingStore.Intent.CloseCalendar -> {
                    scope.launch {
                        publish(BookingStore.Label.CloseCalendar)
                    }
                }

                is BookingStore.Intent.ApplyDate -> {
                    scope.launch {
                        publish(BookingStore.Label.CloseCalendar)
                        intent.date?.let { newDate ->
                            dispatch(Msg.BeginningBookingDate(date = newDate))
                        }
                    }
                }

                is BookingStore.Intent.OpenConfirmBooking -> {
                    scope.launch {

                        bookingInteractor.create(
                            coroutineScope = this@launch,
                            creatingBookModel = CreatingBookModel(
                                workSpaceId = "",
                                dateOfStart = getState().selectedStartDate.atTime(getState().selectedStartTime),
                                dateOfEnd = getState().selectedStartDate.atTime(getState().selectedFinishTime),
                                bookingPeriodUI = getState().bookingPeriodUI,
                                typeOfEndPeriod = TypeEndPeriodBooking.Never
                            )
                        )

                        publish(BookingStore.Label.CloseBookAccept)
                        publish(BookingStore.Label.OpenConfirmBooking)
                    }
                }

                is BookingStore.Intent.OpenMainScreen -> {
                    //TODO()
                }

                is BookingStore.Intent.CloseConfirmBooking -> {
                    scope.launch {
                        publish(BookingStore.Label.CloseConfirmBooking)
                    }

                }

                is BookingStore.Intent.ApplyTime -> {
                    scope.launch {
                        if (intent.isStart) {
                            dispatch(Msg.BeginningBookingTime(intent.time))
                        } else {
                            dispatch(Msg.EndBookingTime(intent.time))
                        }
                        publish(BookingStore.Label.CloseStartTimeModal)
                    }
                }

                is BookingStore.Intent.CloseStartTimeModal -> {
                    scope.launch {
                        publish(BookingStore.Label.CloseStartTimeModal)
                    }

                }

                is BookingStore.Intent.OpenStartTimeModal -> {
                    scope.launch {
                        dispatch(Msg.IsStartTimePicked(isStart = intent.isStart))
                        publish(BookingStore.Label.OpenStartTimeModal)
                    }

                }

                is BookingStore.Intent.SearchSuitableOptions -> {
                    scope.launch {
                        publish(BookingStore.Label.CloseBookPeriod)
                    }

                }

                is BookingStore.Intent.OpenBookRepeat -> {
                    scope.launch {
                        publish(BookingStore.Label.CloseBookPeriod)
                        publish(BookingStore.Label.CloseRepeatDialog)
                        with(intent.pair) {
                            if (second == BookingPeriodUI.Another)
                                publish(BookingStore.Label.OpenBookRepeat)
                            val name = when (second) {
                                is BookingPeriodUI.EveryWorkDay -> MainRes.strings.every_work_day
                                is BookingPeriodUI.Month -> MainRes.strings.every_month
                                is BookingPeriodUI.NoPeriod -> MainRes.strings.booking_not_repeat
                                is BookingPeriodUI.Week -> MainRes.strings.every_week
                                is BookingPeriodUI.Year -> MainRes.strings.every_month
                                is BookingPeriodUI.Another -> MainRes.strings.every_year
                            }
                            dispatch(Msg.ChangeBookingRepeat(bookingRepeat = name))
                            dispatch(Msg.ChangeBookingPeriod(bookingPeriodUI = intent.pair.second))
                        }
                    }

                }

                is BookingStore.Intent.CloseBookRepeat -> {
                    scope.launch {
                        publish(BookingStore.Label.CloseBookRepeat)
                        publish(BookingStore.Label.OpenBookPeriod)
                    }
                }

                is BookingStore.Intent.ChangeSelectedWorkSpacesZone -> {
                    scope.launch {
                        dispatch(Msg.ChangeWorkSpacesUI(workSpacesUI = getSpacesUI(
                            workSpaceZone = intent.workSpaceZone,
                            state = getState()
                        )))
                        dispatch(Msg.ChangeSelectedWorkSpacesZone(intent.workSpaceZone))
                    }
                }

                is BookingStore.Intent.ChangeWorkSpacesUI -> {
                    dispatch(Msg.ChangeWorkSpacesUI(workSpacesUI = intent.workSpaces))
                }

                is BookingStore.Intent.ChangeType -> {

                }

                is BookingStore.Intent.ChangeWholeDay -> {
                    dispatch(Msg.WholeDay(wholeDay = intent.wholeDay))
                    dispatch(Msg.BeginningBookingTime(time = LocalTime(hour = 8, minute = 0)))
                    dispatch(Msg.EndBookingTime(time = LocalTime(hour = 20, minute = 0)))
                    dispatch(Msg.BeginningBookingDate(date = getCurrentDate()))
                    dispatch(Msg.EndBookingDate(date = getCurrentDate()))
                }

                BookingStore.Intent.OpenFinishTimeModal -> {
                    scope.launch {
                        publish(BookingStore.Label.OpenFinishTimeModal)
                    }
                }

                BookingStore.Intent.CloseFinishTimeModal -> {
                    scope.launch {
                        publish(BookingStore.Label.CloseFinishTimeModal)
                    }

                }

                is BookingStore.Intent.ChangeFrequency -> {
                    scope.launch {
                        dispatch(Msg.ChangeFrequency(frequency = intent.frequency))
                        publish(BookingStore.Label.CloseBookRepeat)
                    }
                }

                is BookingStore.Intent.ChangeBookingRepeat -> {
                    scope.launch {
                        dispatch(Msg.ChangeBookingRepeat(bookingRepeat = intent.bookingRepeat))
                    }
                }

                is BookingStore.Intent.ChangeSelectedType -> {
                    dispatch(Msg.SelectedTypeList(type = intent.selectedType))
                }
            }
        }

        override fun executeAction(action: Action, getState: () -> BookingStore.State) =
            when (action) {
                Action.InitWorkSpaces -> dispatch(Msg.ChangeWorkSpacesUI(workSpacesUI = getSpacesUI(
                    workSpaceZone = getState().workSpacesZone,
                    state = getState())
                ))
            }

        private fun getSpacesUI(workSpaceZone: List<WorkSpaceZone>, state: BookingStore.State): List<WorkSpaceUI>{
            val list = workSpaceZone.filter { it.isSelected }
            val listWorkSpaces = state.workSpacesAll
            val newList = mutableListOf<WorkSpaceUI>()
            list.forEach {
                val nameSpace = it.name
                newList.add(listWorkSpaces.first { it.workSpaceName == nameSpace })
            }
            return newList.toList()
        }
    }

    private object ReducerImpl : Reducer<BookingStore.State, Msg> {
        override fun BookingStore.State.reduce(msg: Msg): BookingStore.State {
            return when (msg) {
                is Msg.ChangeSelectedWorkSpacesZone -> copy(workSpacesZone = msg.workSpacesZone)
                is Msg.DateBooking -> copy(selectedStartDate = msg.date)
                is Msg.TimeBooking -> copy(selectedStartTime = msg.time)
                is Msg.SelectedTypeList -> copy(
                    workSpacesType = msg.type.type,
                    selectedType = msg.type
                )
                is Msg.ChangeWorkSpacesUI -> copy(workSpaces = msg.workSpacesUI)
                is Msg.WholeDay -> copy(wholeDay = !msg.wholeDay)
                is Msg.BeginningBookingTime -> copy(selectedStartTime = msg.time)
                is Msg.EndBookingTime -> copy(selectedFinishTime = msg.time)
                is Msg.IsStartTimePicked -> copy(isStart = msg.isStart)
                is Msg.BeginningBookingDate -> copy(selectedStartDate = msg.date)
                is Msg.ChangeFrequency -> copy(frequency = msg.frequency)
                is Msg.ChangeBookingRepeat -> copy(repeatBooking = msg.bookingRepeat)
                is Msg.ChangeBookingPeriod -> copy(bookingPeriodUI = msg.bookingPeriodUI)
                is Msg.ChangeWorkingUI -> copy(bookingInfo = msg.bookingInfo)
                is Msg.EndBookingDate -> copy(selectedFinishDate = msg.date)
            }
        }
    }
}