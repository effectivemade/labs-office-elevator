package band.effective.office.elevator.ui.booking.store

import band.effective.office.elevator.ui.booking.models.WorkSpaceZone
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import org.koin.core.component.KoinComponent

class BookingStoreFactory(private val storeFactory: StoreFactory) : KoinComponent {

    @OptIn(ExperimentalMviKotlinApi::class)
    fun create(): BookingStore =
        object : BookingStore,
            Store<BookingStore.Intent, BookingStore.State, BookingStore.Label> by storeFactory.create(
                name = "BookingStore",
                initialState = BookingStore.State.initState,
                executorFactory = ::ExecutorImpl,
                reducer = ReducerImpl
            ) {}

    private sealed interface Msg {
        data class BeginningBookingTime(val time: LocalTime)
        data class BeginningBookingDate(val date: LocalDate)
        data class EndBookingTime(val time: LocalTime)
        data class EndBookingDate(val date: LocalDate)
        data class TypeList(val type: String) : Msg
        data class DateBooking(val date: LocalDate) : Msg
        data class TimeBooking(val time: LocalTime) : Msg

        data class ChangeSelectedWorkSpacesZone(val workSpacesZone: List<WorkSpaceZone>) : Msg
    }

    private inner class ExecutorImpl :
        CoroutineExecutor<BookingStore.Intent, Nothing, BookingStore.State, Msg, BookingStore.Label>() {
        override fun executeIntent(
            intent: BookingStore.Intent,
            getState: () -> BookingStore.State
        ) {
            when (intent) {
                is BookingStore.Intent.ShowPlace -> dispatch(
                    Msg.TypeList(
                        type = intent.type
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
                            dispatch(Msg.DateBooking(date = newDate))
                        }
                    }
                }

                is BookingStore.Intent.OpenConfirmBooking -> {
                    scope.launch {
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
                        publish(BookingStore.Label.CloseTimeModal)
                        intent.time?.let { newTime ->
                            dispatch(Msg.TimeBooking(newTime))
                        }
                    }
                }

                is BookingStore.Intent.CloseTimeModal -> {
                    scope.launch {
                        publish(BookingStore.Label.CloseTimeModal)
                    }

                }

                is BookingStore.Intent.OpenTimeModal -> {
                    scope.launch {
                        publish(BookingStore.Label.OpenTimeModal)
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
            }
        }
    }

    private object ReducerImpl : Reducer<BookingStore.State, Msg> {
        override fun BookingStore.State.reduce(msg: Msg): BookingStore.State {
            return when(msg) {
                is Msg.ChangeSelectedWorkSpacesZone -> copy(workSpacesZone = msg.workSpacesZone)
                is Msg.DateBooking -> TODO()
                is Msg.TimeBooking -> TODO()
                is Msg.TypeList -> TODO()
            }
        }
    }
}