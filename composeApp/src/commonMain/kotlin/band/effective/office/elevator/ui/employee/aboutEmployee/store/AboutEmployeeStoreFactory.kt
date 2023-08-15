package band.effective.office.elevator.ui.employee.aboutEmployee.store

import band.effective.office.elevator.domain.models.EmployeeInfo
import band.effective.office.elevator.domain.models.User
import band.effective.office.elevator.domain.useCase.AboutEmployeeUseCase
import band.effective.office.elevator.expects.makeCall
import band.effective.office.elevator.expects.pickSBP
import band.effective.office.elevator.expects.pickTelegram
import band.effective.office.elevator.ui.employee.aboutEmployee.models.BookingsFilter
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
    private var recentDate = getCurrentDate()
    private var filtration = BookingsFilter(meetRoom = true, workPlace = true)
    private var datedList = false

    @OptIn(ExperimentalMviKotlinApi::class)
    fun create(): AboutEmployeeStore =
        object : AboutEmployeeStore, Store<Intent, State, Label> by storeFactory.create(
            name = "AboutEmployeeStore",
            initialState = State(
                mokValueUser,
                reservedSeatsList = listOf(),
                currentDate = getCurrentDate(),
                dateFiltrationOnReserves = false,
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
                        reservedSeatsList = msg.reservedSeatsList,
                        filtrationOnReserves = msg.filtrationOnReserves
                )
                is Msg.UpdateSeatsReservation -> {
                    copy(
                        reservedSeatsList = msg.reservedSeatsList,
                        dateFiltrationOnReserves=msg.dateFiltrationOnReserves,
                        filtrationOnReserves = msg.filtrationOnReserves)
                }
            }
    }

    private sealed interface Action {
        data class FetchUserInfo (val employee: User) : Action
    }

    private sealed interface Msg {
        data class ProfileData(
            val user: User,
            val reservedSeatsList: List<ReservedSeat>,
            val filtrationOnReserves: Boolean) : Msg
        data class UpdateSeatsReservation(
            val reservedSeatsList: List<ReservedSeat>,
            val dateFiltrationOnReserves: Boolean,
            val filtrationOnReserves: Boolean) : Msg
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
                        datedList = true
                        intent.date?.let { newDate ->
                            fetchUserInfoByDate(
                                date=newDate,
                                ownerId = mokValueUser.id,
                                bookingsFilter = filtration)
                        }
                    }
                }
                Intent.OpenBottomDialog ->{
                    scope.launch {
                        publish(Label.OpenBottomDialog)
                    }
                }
                is Intent.CloseBottomDialog ->{
                    scope.launch {
                        publish(Label.CloseBottomDialog)
                        intent.bookingsFilter.let { bookingsFilter ->
                            if (datedList) {
                                fetchUserInfoByDate(
                                    date = recentDate,
                                    ownerId = mokValueUser.id,
                                    bookingsFilter = bookingsFilter)
                            }else{
                                fetchUserInfo(
                                    employee = mokValueUser,
                                    bookingsFilter=bookingsFilter)
                            }
                        }
                    }
                }
            }
        }

        override fun executeAction(action: Action, getState: () -> State) {
            when (action) {
                is Action.FetchUserInfo -> fetchUserInfo(employee = action.employee, bookingsFilter = filtration)
            }
        }

        private fun fetchUserInfoByDate(date: LocalDate, ownerId:String, bookingsFilter: BookingsFilter) {
            if(recentDate != date)
                recentDate = date
            else
                filtration = bookingsFilter

            scope.launch (Dispatchers.IO){
                aboutEmployeeUseCase
                    .getBookingsByDate(
                        date = date,
                        ownerId = ownerId,
                        bookingsFilter = bookingsFilter,
                        coroutineScope = this)
                    .collect{newList -> withContext(Dispatchers.Main){
                        dispatch(Msg.UpdateSeatsReservation(
                            reservedSeatsList = newList,
                            dateFiltrationOnReserves = true,
                            filtrationOnReserves = !(filtration.workPlace && filtration.meetRoom)))
                        }
                    }
            }
        }

        private fun fetchUserInfo(employee: User, bookingsFilter: BookingsFilter){
            if (mokValueUser!=employee)
                mokValueUser = employee
            else
                filtration = bookingsFilter

            scope.launch (Dispatchers.IO){
                aboutEmployeeUseCase
                    .getBookingsForUser(
                        ownerId = employee.id,
                        bookingsFilter = bookingsFilter,
                        coroutineScope = this)
                    .collect{newList -> withContext(Dispatchers.Main){
                        dispatch(Msg.ProfileData(
                            user = employee,
                            reservedSeatsList = newList,
                            filtrationOnReserves = !(filtration.workPlace && filtration.meetRoom)))
                        }
                    }
            }
        }
    }
}
