package band.effective.office.elevator.ui.employee.aboutEmployee.store

import band.effective.office.elevator.domain.models.EmployeeInfo
import band.effective.office.elevator.domain.models.User
import band.effective.office.elevator.domain.useCase.AboutEmployeeUseCase
import band.effective.office.elevator.expects.makeCall
import band.effective.office.elevator.expects.pickSBP
import band.effective.office.elevator.expects.pickTelegram
import band.effective.office.elevator.ui.employee.aboutEmployee.models.toUIAbout
import band.effective.office.elevator.ui.employee.aboutEmployee.store.AboutEmployeeStore.*
import band.effective.office.elevator.ui.models.ReservedSeat
import band.effective.office.elevator.utils.getCurrentDate
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineBootstrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.datetime.LocalDate
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AboutEmployeeStoreFactory(private val storeFactory: StoreFactory, private val employee: EmployeeInfo) : KoinComponent {

    private val aboutEmployeeUseCase: AboutEmployeeUseCase by inject()
    private var mokValueUser = EmployeeInfo.defaultEmployee.toUIAbout()

    @OptIn(ExperimentalMviKotlinApi::class)
    fun create(): AboutEmployeeStore =
        object : AboutEmployeeStore, Store<Intent, State, Label> by storeFactory.create(
            name = "AboutEmployeeStore",
            initialState = State(
                mokValueUser,
                reservedSeats = listOf(),
                currentDate = getCurrentDate(),
                filtrationOnReserves = false
            ),
            bootstrapper = coroutineBootstrapper {
                launch {
                    dispatch(Action.FetchUserInfo(employee = employee.toUIAbout()))
                }
            },
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(msg: Msg): State =
            when (msg) {
                is Msg.ProfileData ->
                    copy(
                        user = msg.user,
                        reservedSeats = msg.reservedSeats
                )
                is Msg.UpdateSeatsReservation -> {
                    copy(
                        reservedSeats = msg.reservedSeats,
                        filtrationOnReserves=msg.filtrationOnReserves)
                }
            }
    }

    private sealed interface Action {
        data class FetchUserInfo (val employee: User) : Action
    }

    private sealed interface Msg {
        data class ProfileData(val user: User, val reservedSeats: List<ReservedSeat>) : Msg
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
                Intent.OpenCalendarClicked -> {
                    scope.launch {
                        publish(Label.OpenCalendar)
                    }
                }
                Intent.CloseCalendarClicked -> {
                    scope.launch {
                        publish(Label.CloseCalendar)
                    }
                }
                is Intent.OnClickApplyDate ->{
                    scope.launch {
                        publish(Label.CloseCalendar)
                        intent.date?.let { newDate ->
                            fetchUserInfoByDate(date=newDate, ownerId = mokValueUser.id)
                        }
                    }
                }
                Intent.OpenBottomDialog ->{
                    scope.launch {
                        publish(Label.OpenBottomDialog)
                    }
                }
                Intent.CloseBottomDialog ->{
                    scope.launch {
                        publish(Label.CloseBottomDialog)
                    }
                }
            }
        }

        override fun executeAction(action: Action, getState: () -> State) {
            when (action) {
                is Action.FetchUserInfo -> fetchUserInfo(employee = action.employee)
            }
        }

        private fun fetchUserInfoByDate(date: LocalDate, ownerId:String,) {
            scope.launch (Dispatchers.IO){
                aboutEmployeeUseCase
                    .getBookingsByDate(date = date, ownerId = ownerId, coroutineScope = this)
                    .collect{newList -> withContext(Dispatchers.Main){
                        dispatch(Msg.UpdateSeatsReservation(reservedSeats = newList, filtrationOnReserves = true))
                        }
                    }
            }
        }

        private fun fetchUserInfo(employee: User){
            mokValueUser=employee
            scope.launch (Dispatchers.IO){
                aboutEmployeeUseCase.getBookingsForUser(ownerId = employee.id, coroutineScope = this)
                    .collect{newList -> withContext(Dispatchers.Main){
                        dispatch(Msg.ProfileData(user = employee, reservedSeats = newList))
                    } }
            }
        }
    }
}
