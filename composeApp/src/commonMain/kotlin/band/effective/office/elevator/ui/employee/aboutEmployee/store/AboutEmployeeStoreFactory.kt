package band.effective.office.elevator.ui.employee.aboutEmployee.store

import band.effective.office.elevator.domain.models.EmployeeInfo
import band.effective.office.elevator.domain.models.User
import band.effective.office.elevator.domain.useCase.AboutEmployeeInteractor
import band.effective.office.elevator.expects.makeCall
import band.effective.office.elevator.expects.pickSBP
import band.effective.office.elevator.expects.pickTelegram
import band.effective.office.elevator.ui.employee.aboutEmployee.models.BookingsFilter
import band.effective.office.elevator.ui.employee.aboutEmployee.models.toUIAbout
import band.effective.office.elevator.ui.employee.aboutEmployee.store.AboutEmployeeStore.*
import band.effective.office.elevator.ui.models.ReservedSeat
import band.effective.office.elevator.utils.getCurrentDate
import band.effective.office.network.model.Either
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineBootstrapper
import com.commandiron.wheel_picker_compose.utils.Max
import com.commandiron.wheel_picker_compose.utils.Min
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AboutEmployeeStoreFactory(
    private val storeFactory: StoreFactory,
    private val employeeInfo: EmployeeInfo
) : KoinComponent {

    private val aboutEmployeeInteractor: AboutEmployeeInteractor by inject()
    private var currentUser = employeeInfo.toUIAbout()
    private var recentDate = getCurrentDate()
    private var filtration = BookingsFilter(meetRoom = true, workPlace = true)
    private var datedList = false

    @OptIn(ExperimentalMviKotlinApi::class)
    fun create(): AboutEmployeeStore =
        object : AboutEmployeeStore, Store<Intent, State, Label> by storeFactory.create(
            name = "AboutEmployeeStore",
            initialState = State(
                currentUser,
                reservedSeatsList = listOf(),
                currentDate = getCurrentDate(),
                dateFiltrationOnReserves = datedList,
                filtrationOnReserves = false
            ),
            bootstrapper = coroutineBootstrapper {
                launch {
                    dispatch(Action.FetchUserInfo(employee = employeeInfo.toUIAbout()))
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
                        filtrationOnReserves = msg.filtrationOnReserves,
                        isLoading = false
                    )

                is Msg.UpdateSeatsReservation -> {
                    copy(
                        currentDate = msg.date,
                        reservedSeatsList = msg.reservedSeatsList,
                        dateFiltrationOnReserves = msg.dateFiltrationOnReserves,
                        filtrationOnReserves = msg.filtrationOnReserves,
                        isLoading = false
                    )
                }
            }
    }

    private sealed interface Action {
        data class FetchUserInfo(val employee: User) : Action
    }

    private sealed interface Msg {
        data class ProfileData(
            val user: User,
            val reservedSeatsList: List<ReservedSeat>,
            val filtrationOnReserves: Boolean
        ) : Msg

        data class UpdateSeatsReservation(
            val date: LocalDate,
            val reservedSeatsList: List<ReservedSeat>,
            val dateFiltrationOnReserves: Boolean,
            val filtrationOnReserves: Boolean
        ) : Msg
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

                is Intent.OnClickApplyDate -> {
                    scope.launch {
                        publish(Label.CloseCalendar)
                        datedList = true
                        intent.date?.let { newDate ->
                            fetchUserInfoByDate(
                                date = newDate,
                                ownerId = currentUser.id,
                                bookingsFilter = filtration
                            )
                        }
                    }
                }

                Intent.OpenBottomDialog -> {
                    scope.launch {
                        publish(Label.OpenBottomDialog)
                    }
                }

                is Intent.CloseBottomDialog -> {
                    scope.launch {
                        publish(Label.CloseBottomDialog)
                        intent.bookingsFilter.let { bookingsFilter ->
                            if (datedList) {
                                fetchUserInfoByDate(
                                    date = recentDate,
                                    ownerId = currentUser.id,
                                    bookingsFilter = bookingsFilter
                                )
                            } else {
                                fetchUserInfo(
                                    date = getState().currentDate,
                                    employee = currentUser,
                                    bookingsFilter = bookingsFilter
                                )
                            }
                        }
                    }
                }
            }
        }

        override fun executeAction(action: Action, getState: () -> State) {
            when (action) {
                is Action.FetchUserInfo -> fetchUserInfo(
                    date = getState().currentDate,
                    employee = action.employee,
                    bookingsFilter = filtration
                )
            }
        }

        private fun fetchUserInfoByDate(
            date: LocalDate,
            ownerId: String,
            bookingsFilter: BookingsFilter
        ) {
            if (recentDate != date)
                recentDate = date
            else
                filtration = bookingsFilter

            scope.launch(Dispatchers.IO) {
                aboutEmployeeInteractor
                    .getBookingsForUser(
                        bookingsFilter = bookingsFilter,
                        ownerId = ownerId,
                        beginDateTime = LocalDateTime(date = date, time = LocalTime.Min),
                        endDateTime = LocalDateTime(date = date, time = LocalTime.Max),
                    )
                    .collect { newList ->
                        withContext(Dispatchers.Main) {
                            when (newList) {
                                is Either.Error -> {
                                    //TODO show error on UI
                                }

                                is Either.Success -> {
                                    dispatch(
                                        Msg.UpdateSeatsReservation(
                                            date = recentDate,
                                            reservedSeatsList = newList.data,
                                            dateFiltrationOnReserves = datedList,
                                            filtrationOnReserves = !(filtration.workPlace && filtration.meetRoom)
                                        )
                                    )
                                }
                            }
                        }
                    }
            }
        }

        private fun fetchUserInfo(
            date: LocalDate,
            employee: User,
            bookingsFilter: BookingsFilter
        ) {
            scope.launch(Dispatchers.IO) {
                aboutEmployeeInteractor
                    .getBookingsForUser(
                        bookingsFilter = bookingsFilter,
                        ownerId = employee.id,
                        beginDateTime = LocalDateTime(date = date, time = LocalTime.Min),
                        endDateTime = LocalDateTime(date = date, time = LocalTime.Max),
                    ).collect { newList ->
                        withContext(Dispatchers.Main) {
                            when (newList) {
                                is Either.Error -> {
                                    //TODO show error on UI
                                }
                                is Either.Success -> {
                                    dispatch(
                                        Msg.ProfileData(
                                            user = employee,
                                            reservedSeatsList = newList.data,
                                            filtrationOnReserves = !(filtration.workPlace && filtration.meetRoom)
                                        )
                                    )
                                }
                            }
                        }
                    }
            }
        }
    }
}
