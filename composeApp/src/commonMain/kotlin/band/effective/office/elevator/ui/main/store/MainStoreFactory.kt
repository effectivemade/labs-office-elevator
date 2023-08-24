package band.effective.office.elevator.ui.main.store

import band.effective.office.elevator.MainRes
import band.effective.office.elevator.data.ApiResponse
import band.effective.office.elevator.domain.useCase.DeleteBookingUseCase
import band.effective.office.elevator.domain.entity.BookingInteractor
import band.effective.office.elevator.domain.useCase.ElevatorCallUseCase
import band.effective.office.elevator.domain.useCase.GetBookingsUseCase
import band.effective.office.elevator.expects.showToast
import band.effective.office.elevator.ui.employee.aboutEmployee.models.BookingsFilter
import band.effective.office.elevator.ui.models.ElevatorState
import band.effective.office.elevator.ui.models.ReservedSeat
import band.effective.office.elevator.utils.getCurrentDate
import band.effective.office.network.model.Either
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.utils.ExperimentalMviKotlinApi
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineBootstrapper
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.format
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.datetime.LocalDate
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal class MainStoreFactory(
    private val storeFactory: StoreFactory
) : KoinComponent {

    private val elevatorUseCase: ElevatorCallUseCase by inject()
    private val bookingsUseCase: GetBookingsUseCase by inject()
    private val deleteBookingUseCase : DeleteBookingUseCase by inject()
    private var recentDate = LocalDate(2023,8,16)
    private val bookingInteractor: BookingInteractor by inject()

    private var filtration = BookingsFilter(meetRoom = true, workPlace = true)
    private var updatedList = false

    @OptIn(ExperimentalMviKotlinApi::class)
    fun create(): MainStore =
        object : MainStore,
            Store<MainStore.Intent, MainStore.State, MainStore.Label> by storeFactory.create(
                name = "MainStore",
                initialState = MainStore.State(
                    elevatorState = ElevatorState.Below,
                    reservedSeats = listOf(),
                    currentDate = getCurrentDate(),
                    dateFiltrationOnReserves = updatedList,
                    idSelectedBooking = ""
                ),
                executorFactory = ::ExecutorImpl,
                reducer = ReducerImpl,
                bootstrapper = coroutineBootstrapper {
                    launch {
                        dispatch(
                            Action.LoadBookings(date = getCurrentDate())
                        )
                    }
                }

            ) {}

    private sealed interface Msg {
        data class UpdateElevatorState(val elevatorState: ElevatorState) : Msg
        data class UpdateSeatsReservation(val reservedSeats: List<ReservedSeat>) : Msg

        data class UpdateBookingSelectedId(val bookingId: String) : Msg

        data class UpdateSeatReservationByDate(
            val date: LocalDate,
            val reservedSeats: List<ReservedSeat>,
            val dateFiltrationOnReserves: Boolean
        ) : Msg
    }

    private sealed interface Action {
        data class LoadBookings(val date: LocalDate) : Action
    }

    private inner class ExecutorImpl :
        CoroutineExecutor<MainStore.Intent, Action, MainStore.State, Msg, MainStore.Label>() {
        override fun executeIntent(intent: MainStore.Intent, getState: () -> MainStore.State) {
            when (intent) {
                MainStore.Intent.OnClickCallElevator -> {
                    if (getState().elevatorState is ElevatorState.Below)
                        doElevatorCall()
                }

                is MainStore.Intent.OnClickShowOption -> {
                    scope.launch {
                        dispatch(Msg.UpdateBookingSelectedId(bookingId = intent.bookingId))
                        publish(MainStore.Label.ShowOptions)
                    }
                }

                MainStore.Intent.OnClickCloseCalendar -> {
                    scope.launch {
                        publish(MainStore.Label.CloseCalendar)
                    }
                }

                MainStore.Intent.OnClickOpenCalendar -> {
                    scope.launch {
                        publish(MainStore.Label.OpenCalendar)
                    }
                }

                is MainStore.Intent.OnClickApplyDate -> {
                    publish(MainStore.Label.CloseCalendar)
                    updatedList = true
                    intent.date?.let { newDate ->
                        changeBookingsByDate(date = newDate, bookingsFilter = filtration)
                    }
                }

//                MainStore.Intent.OnClickShowMap -> {
//                    publish(MainStore.Label.OpenBooking)
//                }

                is MainStore.Intent.OnClickDeleteBooking -> {
                    scope.launch(Dispatchers.IO) {
                        bookingInteractor.deleteBooking(getState().idSelectedBooking)
                    }
                    scope.launch {
                        publish(MainStore.Label.CloseOption)
                    }
                }

                MainStore.Intent.OpenFiltersBottomDialog -> {
                    scope.launch {
                        publish(MainStore.Label.OpenFiltersBottomDialog)
                    }
                }

                is MainStore.Intent.CloseFiltersBottomDialog -> {
                    scope.launch {
                        publish(MainStore.Label.CloseFiltersBottomDialog)
                        intent.bookingsFilter.let { bookingsFilter ->
                            if (updatedList) {
                                changeBookingsByDate(
                                    date = recentDate,
                                    bookingsFilter = bookingsFilter
                                )
                            } else {
                                getBookingsForUserByDate(
                                    date = getCurrentDate(),
                                    bookingsFilter = bookingsFilter
                                )
                            }
                        }
                    }
                }

                else -> {}
            }
        }

        private fun deleteBooking(seat:ReservedSeat) {
            scope.launch(Dispatchers.IO){
                deleteBookingUseCase.deleteBooking(
                    seat = seat,
                    coroutineScope = this
                )
            }
        }

        override fun executeAction(action: Action, getState: () -> MainStore.State) {
            when (action) {
                is Action.LoadBookings -> getBookingsForUserByDate(
                    date = action.date,
                    bookingsFilter = filtration
                )
            }
        }

        private fun doElevatorCall() {
            scope.launch {
                dispatch(Msg.UpdateElevatorState(ElevatorState.Goes))
                delay(1000)
                publish(
                    when (val result = elevatorUseCase.callElevator()) {
                        is ApiResponse.Error.HttpError -> MainStore.Label.ShowError(
                            MainStore.ErrorState(
                                (MainRes.strings.server_error.format(result.code.toString()))
                                        as StringResource
                            )
                        )

                        ApiResponse.Error.NetworkError -> MainStore.Label.ShowError(
                            MainStore.ErrorState(
                                MainRes.strings.network_error
                            )
                        )

                        ApiResponse.Error.SerializationError -> MainStore.Label.ShowError(
                            MainStore.ErrorState(
                                MainRes.strings.developer_error
                            )
                        )

                        ApiResponse.Error.UnknownError -> MainStore.Label.ShowError(
                            MainStore.ErrorState(
                                MainRes.strings.developer_error
                            )
                        )

                        is ApiResponse.Success -> {
                            MainStore.Label.ShowSuccess
                        }
                    }
                )
                delay(1000)
                dispatch(Msg.UpdateElevatorState(ElevatorState.Raised))
            }
        }

        fun getBookingsForUserByDate(date: LocalDate, bookingsFilter: BookingsFilter) {
            if (recentDate != date)
                recentDate = date
            else
                filtration = bookingsFilter

            scope.launch(Dispatchers.IO) {
                bookingInteractor
                    .getByDate(date = date)
                    .collect { bookings ->
                        withContext(Dispatchers.Main) {
                            when(bookings) {
                                is Either.Error -> {
                                    // TODO show error on UI
                                }
                                is Either.Success -> {
                                    dispatch(Msg.UpdateSeatsReservation(reservedSeats = bookings.data))
                                }
                            }
                        }
                    }
            }
        }

        fun changeBookingsByDate(date: LocalDate, bookingsFilter: BookingsFilter) {
            if (recentDate != date)
                recentDate = date
            else
                filtration = bookingsFilter

            scope.launch(Dispatchers.IO) {
                bookingInteractor
                    .getByDate(date = date)
                    .collect { bookings ->
                        withContext(Dispatchers.Main) {
                            withContext(Dispatchers.Main) {
                                when(bookings) {
                                    is Either.Error -> {
                                        // TODO show error on UI
                                    }
                                    is Either.Success -> {
                                        dispatch(
                                            Msg.UpdateSeatReservationByDate(
                                                date = recentDate,
                                                reservedSeats = bookings.data,
                                                dateFiltrationOnReserves = updatedList
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

    private object ReducerImpl : Reducer<MainStore.State, Msg> {
        override fun MainStore.State.reduce(message: Msg): MainStore.State =
            when (message) {
                is Msg.UpdateElevatorState -> copy(elevatorState = message.elevatorState)
                is Msg.UpdateSeatsReservation -> copy(reservedSeats = message.reservedSeats)
                is Msg.UpdateSeatReservationByDate -> copy(
                    currentDate = message.date,
                    reservedSeats = message.reservedSeats,
                    dateFiltrationOnReserves = message.dateFiltrationOnReserves
                )

                is Msg.UpdateBookingSelectedId -> copy(idSelectedBooking = message.bookingId)
            }
    }
}


