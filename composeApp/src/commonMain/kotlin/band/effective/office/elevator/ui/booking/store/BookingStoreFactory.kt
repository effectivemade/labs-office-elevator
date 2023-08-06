package band.effective.office.elevator.ui.booking.store

import band.effective.office.elevator.utils.getCurrentDate
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import org.koin.core.component.KoinComponent

class BookingStoreFactory(private val storeFactory: StoreFactory): KoinComponent{

    @OptIn(ExperimentalMviKotlinApi::class)
    fun create(): BookingStore =
        object : BookingStore, Store<BookingStore.Intent, BookingStore.State,BookingStore.Label> by storeFactory.create(
            name = "BookingStore",
            initialState = BookingStore.State(
                listPlace = listOf(),
                currentDate = getCurrentDate()
            ),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl,
        ){}

    private sealed interface Msg {
        data class TypeList(val type: String) : Msg
        data class DateBooking(val date: LocalDate): Msg
    }

    private inner class ExecutorImpl:
            CoroutineExecutor<BookingStore.Intent,Nothing,BookingStore.State,Msg,BookingStore.Label>(){
                override fun executeIntent(intent: BookingStore.Intent, getState:()->BookingStore.State){
                    when (intent){
                        is BookingStore.Intent.ShowPlace -> dispatch(
                            Msg.TypeList (
                                type = intent.type
                            )
                        )
                        is BookingStore.Intent.OpenChooseZone ->{
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
                                publish(BookingStore.Label.CloseBookAccept)
                                publish(BookingStore.Label.OpenBookPeriod)

                            }
                        }
                        is BookingStore.Intent.CloseBookPeriod -> {
                            scope.launch {
                                publish(BookingStore.Label.CloseBookPeriod)
                                publish(BookingStore.Label.OpenBookAccept)
                            }
                        }
                        is BookingStore.Intent.OpenRepeatDialog-> {
                            scope.launch {
                                publish(BookingStore.Label.OpenRepeatDialog)
                            }
                        }
                        is BookingStore.Intent.CloseRepeatDialog -> {
                            scope.launch {
                                publish(BookingStore.Label.CloseRepeatDialog)
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
                            scope.launch{
                                publish(BookingStore.Label.CloseCalendar)
                            }
                        }
                        is BookingStore.Intent.ApplyDate -> {
                            publish(BookingStore.Label.CloseCalendar)
                            intent.date?.let { newDate ->
                                dispatch(Msg.DateBooking(date = newDate))
                            }
                        }
                    }
                }


            }

    private object ReducerImpl : Reducer<BookingStore.State, Msg> {
        override fun BookingStore.State.reduce(msg: Msg): BookingStore.State {
            TODO("Not yet implemented")
        }

    }
}