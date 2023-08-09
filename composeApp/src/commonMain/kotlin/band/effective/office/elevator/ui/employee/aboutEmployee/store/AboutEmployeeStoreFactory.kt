package band.effective.office.elevator.ui.employee.aboutEmployee.store

import band.effective.office.elevator.domain.models.User
import band.effective.office.elevator.expects.makeCall
import band.effective.office.elevator.expects.pickSBP
import band.effective.office.elevator.expects.pickTelegram
import band.effective.office.elevator.ui.employee.aboutEmployee.store.AboutEmployeeStore.*
import band.effective.office.elevator.ui.models.ReservedSeat
import band.effective.office.elevator.utils.getCurrentDate
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineBootstrapper
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import org.koin.core.component.KoinComponent

class AboutEmployeeStoreFactory(private val storeFactory: StoreFactory) : KoinComponent {

    @OptIn(ExperimentalMviKotlinApi::class)
    fun create(): AboutEmployeeStore =
        object : AboutEmployeeStore, Store<Intent, State, Label> by storeFactory.create(
            name = "AboutEmployeeStore",
            initialState = State(
                mokValueUser,
                reservedSeats = mokValue,
                currentDate = getCurrentDate(),
                filtrationOnReserves = false
            ),
            bootstrapper = coroutineBootstrapper {
                launch {
                    dispatch(Action.FetchUserInfo(date = getCurrentDate()))
                }
            },
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(msg: Msg): State =
            when (msg) {
                is Msg.ProfileData -> copy(user = mokValueUser)
                is Msg.UpdateSeatsReservation -> {
                    copy(
                        reservedSeats = msg.reservedSeats,
                        filtrationOnReserves=msg.filtrationOnReserves)
                }
            }
    }

    private sealed interface Action {
        data class FetchUserInfo(val date: LocalDate) : Action
    }

    private sealed interface Msg {
        data class ProfileData(val user: User) : Msg
        data class UpdateSeatsReservation(val reservedSeats: List<ReservedSeat>, val filtrationOnReserves: Boolean) : Msg
    }

    private inner class ExecutorImpl :
        CoroutineExecutor<Intent, Action, State, Msg, Label>() {
        override fun executeIntent(intent: Intent, getState: () -> State) {
            when (intent) {
                Intent.BackClicked -> TODO()
                Intent.TelegramClicked -> pickTelegram(getState().user.telegram!!)
                Intent.TelephoneClicked -> makeCall(getState().user.phoneNumber!!)
                Intent.TransferMoneyClicked -> pickSBP(getState().user.phoneNumber!!)
                AboutEmployeeStore.Intent.OpenCalendarClicked -> {
                    scope.launch {
                        publish(AboutEmployeeStore.Label.OpenCalendar)
                    }
                }
                AboutEmployeeStore.Intent.CloseCalendarClicked -> {
                    scope.launch {
                        publish(AboutEmployeeStore.Label.CloseCalendar)
                    }
                }
                is AboutEmployeeStore.Intent.OnClickApplyDate ->{
                    scope.launch {
                        publish(AboutEmployeeStore.Label.CloseCalendar)
                        intent.date?.let { newDate ->
                            fetchUserInfoByDate(newDate)
                        }
                    }
                }
                AboutEmployeeStore.Intent.OpenBottomDialog ->{
                    scope.launch {
                        publish(AboutEmployeeStore.Label.OpenBottomDialog)
                    }
                }
                AboutEmployeeStore.Intent.CloseBottomDialog ->{
                    scope.launch {
                        publish(AboutEmployeeStore.Label.CloseBottomDialog)
                    }
                }
            }
        }

        override fun executeAction(action: Action, getState: () -> State) {
            when (action) {
                is Action.FetchUserInfo -> dispatch(Msg.UpdateSeatsReservation(reservedSeats = mokValue,filtrationOnReserves = false))//fetchUserInfoByDate(date= action.date)
            }
        }

        private fun fetchUserInfoByDate(date: LocalDate) {
            scope.launch {
                dispatch(Msg.UpdateSeatsReservation(reservedSeats = mokValue.filter { it.bookingDate == date }, filtrationOnReserves = true))
            }
        }
    }
}

private val mokValueUser = User("1","1","Ivanov Ivan", "Android-developer","67","@ivanov","employee@effective.com")
private val mokValue = listOf(
    ReservedSeat(
        bookingId = "1",
        ownerId = "1",
        seatName = "Рабочее масто А2",
        bookingDay = "Ср, 16 августа",
        bookingTime = "12:00 - 14:00",
        bookingDate = LocalDate(month = Month.AUGUST, year = 2023, dayOfMonth = 16)
    ),
    ReservedSeat(
        bookingId = "1",
        ownerId = "1",
        seatName = "Переговорная Sun",
        bookingDay = "Чт, 17 августа",
        bookingTime = "14:00 - 16:00",
        bookingDate = LocalDate(month = Month.AUGUST, year = 2023, dayOfMonth = 17)
    ),
)