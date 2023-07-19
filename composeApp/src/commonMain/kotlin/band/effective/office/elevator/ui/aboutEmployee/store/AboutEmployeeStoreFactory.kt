package band.effective.office.elevator.ui.aboutEmployee.store

import band.effective.office.elevator.ui.aboutEmployee.store.AboutEmployeeStore.*
import band.effective.office.elevator.ui.models.ReservedSeat
import band.effective.office.elevator.ui.models.User
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineBootstrapper
import org.koin.core.component.KoinComponent

class AboutEmployeeStoreFactory(private val storeFactory: StoreFactory): KoinComponent {

    @OptIn(ExperimentalMviKotlinApi::class)
    fun create(): AboutEmployeeStore =
        object : AboutEmployeeStore, Store<Intent,State,Nothing> by storeFactory.create(
            name = "AboutEmployeeStore",
            initialState = State(
                User(imageUrl = null, userName = null, telegram = null, post = null, phoneNumber = null, email = null),
                reservedSeats = listOf()
            ),
            bootstrapper = coroutineBootstrapper {
                dispatch(Action.FetchUserInfo)
            },
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(msg: Msg): State =
            when (msg) {
                is Msg.ProfileData -> copy(user = msg.user)
                is Msg.UpdateSeatsReservation -> copy(reservedSeats = msg.reservedSeats)
            }
    }

    private sealed interface Action {
        object FetchUserInfo : Action
    }

    private sealed interface Msg {
        data class ProfileData(val user: User) : Msg
        data class UpdateSeatsReservation(val reservedSeats: List<ReservedSeat>) : Msg
    }

    private inner class ExecutorImpl :
            CoroutineExecutor<Intent, Action, State, Msg,Nothing>(){
                override fun executeIntent(intent: Intent, getState: () ->State){
                    when(intent){
                        Intent.BackClicked -> TODO()
                        Intent.TelegramClicked -> TODO()
                        Intent.TelephoneClicked -> TODO()
                        Intent.TransferMoneyClicked -> TODO()
                    }
                }
        override fun executeAction(action: Action, getState: () -> State) {
            when (action) {
                Action.FetchUserInfo -> fetchUserInfo()
            }
        }

        private fun fetchUserInfo() {

        }
    }
}