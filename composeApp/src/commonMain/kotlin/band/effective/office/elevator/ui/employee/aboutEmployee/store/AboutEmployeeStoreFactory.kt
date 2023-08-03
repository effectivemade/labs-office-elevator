package band.effective.office.elevator.ui.employee.aboutEmployee.store

import band.effective.office.elevator.domain.models.User
import band.effective.office.elevator.expects.makeCall
import band.effective.office.elevator.expects.pickSBP
import band.effective.office.elevator.expects.pickTelegram
import band.effective.office.elevator.ui.employee.aboutEmployee.store.AboutEmployeeStore.*
import band.effective.office.elevator.ui.models.ReservedSeat
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineBootstrapper
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import org.koin.core.component.KoinComponent

class AboutEmployeeStoreFactory(private val storeFactory: StoreFactory) : KoinComponent {

    @OptIn(ExperimentalMviKotlinApi::class)
    fun create(): AboutEmployeeStore =
        object : AboutEmployeeStore, Store<Intent, State, Nothing> by storeFactory.create(
            name = "AboutEmployeeStore",
            initialState = State(
                mokValueUser,
                reservedSeats = mokValue
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
                is Msg.ProfileData -> copy(user = mokValueUser)
                is Msg.UpdateSeatsReservation -> {
                    val reservedSeats = mokValue
                    copy(reservedSeats = reservedSeats)
                }
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
        CoroutineExecutor<Intent, Action, State, Msg, Nothing>() {
        override fun executeIntent(intent: Intent, getState: () -> State) {
            when (intent) {
                Intent.BackClicked -> TODO()
                Intent.TelegramClicked -> pickTelegram(getState().user.telegram!!)
                Intent.TelephoneClicked -> makeCall(getState().user.phoneNumber!!)
                Intent.TransferMoneyClicked -> pickSBP(getState().user.phoneNumber!!)
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

private val mokValueUser = User("1","1","Ivanov Ivan", "Android-developer","67","@ivanov","employee@effective.com")
private val mokValue = listOf(
    ReservedSeat(
        bookingId = 1,
        ownerId = 1,
        seatName = "Рабочее масто А2",
        bookingDay = "Пн, 1 июля",
        bookingTime = "12:00 - 14:00",
        bookingDate = LocalDate(month = Month.JULY, year = 2023, dayOfMonth = 16)
    ),
    ReservedSeat(
        bookingId = 1,
        ownerId = 1,
        seatName = "Переговорная Sun",
        bookingDay = "Вт, 2 июля",
        bookingTime = "14:00 - 16:00",
        bookingDate = LocalDate(month = Month.JULY, year = 2023, dayOfMonth = 17)
    ),
)