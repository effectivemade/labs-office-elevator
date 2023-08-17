package band.effective.office.elevator.ui.booking.store

import band.effective.office.elevator.MainRes
import band.effective.office.elevator.domain.entity.BookingInteractor
import band.effective.office.elevator.domain.models.BookingPeriod
import band.effective.office.elevator.domain.models.CreatingBookModel
import band.effective.office.elevator.domain.models.TypeEndPeriodBooking
import band.effective.office.elevator.ui.booking.models.Frequency
import band.effective.office.elevator.ui.booking.models.WorkSpaceType
import band.effective.office.elevator.ui.booking.models.WorkSpaceUI
import band.effective.office.elevator.ui.booking.models.WorkSpaceZone
import band.effective.office.elevator.ui.models.TypesList
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineBootstrapper
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.atTime
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

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
        data class EndBookingDate(val date: LocalDate)
        data class SelectedTypeList(val type: TypesList) : Msg
        data class DateBooking(val date: LocalDate) : Msg
        data class TimeBooking(val time: LocalTime) : Msg

        data class ChangeSelectedWorkSpacesZone(val workSpacesZone: List<WorkSpaceZone>) : Msg

        data class ChangeWorkSpacesUI(val workSpacesUI: List<WorkSpaceUI>) : Msg

        data class WholeDay(val wholeDay: Boolean) : Msg
        data class IsStartTimePicked(val isStart: Boolean) : Msg
        data class ChangeFrequency(val frequency: Frequency) : Msg
        data class ChangeBookingRepeat(val bookingRepeat: String) : Msg
        data class ChangeBookingPeriod(val bookingPeriod: BookingPeriod) : Msg
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
                        type = TypesList(name = MainRes.strings.app_name, icon = MainRes.images.table_icon, type = WorkSpaceType.WORK_PLACE)
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

                        scope.launch {
                            val list = getState().workSpacesZone.filter { it.isSelected == true }
                                .toMutableList()
                            val mListW = getState().workSpaces.toMutableList()

                            val iteratorW = mListW.iterator()
                            while (iteratorW.hasNext()) {
                                val workSpaceUI = iteratorW.next()
                                val iteratorZ = list.iterator()
                                while (iteratorZ.hasNext()) {
                                    val zone = iteratorZ.next()
                                    if (workSpaceUI.workSpaceName != zone.name) {
                                        iteratorW.remove()
                                        break
                                    }
                                }
                            }
                            dispatch(Msg.ChangeWorkSpacesUI(workSpacesUI = mListW.toList()))
                        }
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
                        publish(BookingStore.Label.OpenBookAccept)
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
                                bookingPeriod = getState().bookingPeriod,
                                typeOfEndPeriod = TypeEndPeriodBooking.Never
                            )
                        )

                        publish(BookingStore.Label.CloseBookAccept)
                        publish(BookingStore.Label.OpenConfirmBooking)
                    }
                }

                is BookingStore.Intent.OpenMainScreen -> {
                    TODO()
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
                        publish(BookingStore.Label.OpenBookRepeat)
                        val name = when (intent.pair.second) {
                            is BookingPeriod.EveryWorkDay -> "Каждый рабочий день"
                            is BookingPeriod.Month -> "Каждый месяц"
                            BookingPeriod.NoPeriod -> "Без периода"
                            is BookingPeriod.Week -> "Каждую неделю"
                            is BookingPeriod.Year -> "Каждый год"
                        }
                        dispatch(Msg.ChangeBookingRepeat(bookingRepeat = name))
                        dispatch(Msg.ChangeBookingPeriod(bookingPeriod = intent.pair.second))
                    }

                }

                is BookingStore.Intent.CloseBookRepeat -> {
                    scope.launch {
                        publish(BookingStore.Label.CloseBookRepeat)
                        publish(BookingStore.Label.OpenBookPeriod)
                    }
                }

                is BookingStore.Intent.ChangeSelectedWorkSpacesZone -> {
                    dispatch(Msg.ChangeSelectedWorkSpacesZone(intent.workSpaceZone))
                }

                is BookingStore.Intent.ChangeWorkSpacesUI -> {
                    val list = getState().workSpacesZone.filter { zone -> zone.isSelected == true }
                    intent.workSpaces.forEachIndexed { index1, workSpaceUI ->
                        getState().workSpacesZone.forEachIndexed { index2, workSpaceZone ->
                            intent.workSpaces.filter { workSpaceUI -> workSpaceUI.workSpaceName == workSpaceZone.name }
                        }
                    }
                    dispatch(Msg.ChangeWorkSpacesUI(workSpacesUI = intent.workSpaces))
                }

                is BookingStore.Intent.ChangeType -> {

                }

                is BookingStore.Intent.ChangeWholeDay -> {
                    dispatch(Msg.WholeDay(wholeDay = intent.wholeDay))
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
                Action.InitWorkSpaces -> initList(getState())
            }

        private fun initList(state: BookingStore.State) {
            scope.launch {
                dispatch(Msg.ChangeWorkSpacesUI(workSpacesUI = state.workSpaces.filter { workSpaceUI -> workSpaceUI.workSpaceType == WorkSpaceType.WORK_PLACE }))
            }
        }
    }

    private object ReducerImpl : Reducer<BookingStore.State, Msg> {
        override fun BookingStore.State.reduce(msg: Msg): BookingStore.State {
            return when (msg) {
                is Msg.ChangeSelectedWorkSpacesZone -> copy(workSpacesZone = msg.workSpacesZone)
                is Msg.DateBooking -> copy(selectedStartDate = msg.date)
                is Msg.TimeBooking -> copy(selectedStartTime = msg.time)
                is Msg.SelectedTypeList -> copy(selectedType = msg.type)
                is Msg.ChangeWorkSpacesUI -> copy(workSpaces = msg.workSpacesUI)
                is Msg.WholeDay -> copy(wholeDay = msg.wholeDay)
                is Msg.BeginningBookingTime -> copy(selectedStartTime = msg.time)
                is Msg.EndBookingTime -> copy(selectedFinishTime = msg.time)
                is Msg.IsStartTimePicked -> copy(isStart = msg.isStart)
                is Msg.BeginningBookingDate -> copy(selectedStartDate = msg.date)
                is Msg.ChangeFrequency -> copy(frequency = msg.frequency)
                is Msg.ChangeBookingRepeat -> copy(repeatBooking = msg.bookingRepeat)
                is Msg.ChangeBookingPeriod -> copy(bookingPeriod = msg.bookingPeriod)
            }
        }
    }
}