package band.effective.office.elevator.ui.booking.store

import band.effective.office.elevator.domain.models.GoogleAccount
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import org.koin.core.component.KoinComponent

class BookingStoreFactory(private val storeFactory: StoreFactory): KoinComponent{

    @OptIn(ExperimentalMviKotlinApi::class)
    fun create(): BookingStore =
        object : BookingStore, Store<BookingStore.Intent, BookingStore.State,Nothing> by storeFactory.create(
            name = "BookingStore",
            initialState = BookingStore.State(
                listMeetingRoom = listOf(),
            ),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl,
        ){}

    private sealed interface Msg {
        data class ProfileData(val user: GoogleAccount) : Msg
    }

    private inner class ExecutorImpl:
            CoroutineExecutor<BookingStore.Intent,Nothing,BookingStore.State,Msg,Nothing>(){
                override fun executeIntent(intent: BookingStore.Intent, getState:()->BookingStore.State){

                }
            }

    private object ReducerImpl : Reducer<BookingStore.State, Msg> {
        override fun BookingStore.State.reduce(msg: Msg): BookingStore.State {
            TODO("Not yet implemented")
        }

    }
}