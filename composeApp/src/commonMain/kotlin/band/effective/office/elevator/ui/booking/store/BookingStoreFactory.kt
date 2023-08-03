package band.effective.office.elevator.ui.booking.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class BookingStoreFactory(private val storeFactory: StoreFactory): KoinComponent{

    @OptIn(ExperimentalMviKotlinApi::class)
    fun create(): BookingStore =
        object : BookingStore, Store<BookingStore.Intent, BookingStore.State,BookingStore.Label> by storeFactory.create(
            name = "BookingStore",
            initialState = BookingStore.State(
                listPlace = listOf(),
            ),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl,
        ){}

    private sealed interface Msg {
        data class TypeList(val type: String) : Msg
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
                                publish(BookingStore.Label.OpenBookPeriod)
                            }
                        }
                        is BookingStore.Intent.CloseBookPeriod -> {
                            scope.launch {
                                publish(BookingStore.Label.CloseBookPeriod)
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

                    }
                }
            }

    private object ReducerImpl : Reducer<BookingStore.State, Msg> {
        override fun BookingStore.State.reduce(msg: Msg): BookingStore.State {
            TODO("Not yet implemented")
        }

    }
}