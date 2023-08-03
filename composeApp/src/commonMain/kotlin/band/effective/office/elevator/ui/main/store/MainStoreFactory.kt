package band.effective.office.elevator.ui.main.store

import band.effective.office.elevator.MainRes
import band.effective.office.elevator.data.ApiResponse
import band.effective.office.elevator.domain.useCase.ElevatorCallUseCase
import band.effective.office.elevator.domain.useCase.GetBookingsUseCase
import band.effective.office.elevator.ui.models.ElevatorState
import band.effective.office.elevator.ui.models.ReservedSeat
import band.effective.office.elevator.utils.getCurrentDate
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
import kotlinx.datetime.Month
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal class MainStoreFactory(
    private val storeFactory: StoreFactory
) : KoinComponent {

    private val elevatorUseCase: ElevatorCallUseCase by inject()
    private val bookingsUseCase: GetBookingsUseCase by inject()

    @OptIn(ExperimentalMviKotlinApi::class)
    fun create(): MainStore =
        object : MainStore,
            Store<MainStore.Intent, MainStore.State, MainStore.Label> by storeFactory.create(
                name = "MainStore",
                initialState = MainStore.State(
                    elevatorState = ElevatorState.Below,
                    reservedSeats = listOf(),
                    currentDate = getCurrentDate()
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

        data class UpdateSeatReservationByDate(
            val date: LocalDate,
            val reservedSeats: List<ReservedSeat>
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

                MainStore.Intent.OnClickShowOption -> {
                    scope.launch {
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
                    intent.date?.let { newDate ->
                        changeBookingsByDate(date = newDate)
                    }
                }
            }
        }

        override fun executeAction(action: Action, getState: () -> MainStore.State) {
            when (action) {
                is Action.LoadBookings -> getBookingsForUserByDate(date = action.date)
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

        fun getBookingsForUserByDate(date: LocalDate) {
            scope.launch(Dispatchers.IO) {
                bookingsUseCase
                    .getBookingsByDate(date = date, coroutineScope = this)
                    .collect { bookings ->
                        withContext(Dispatchers.Main) {
                            dispatch(Msg.UpdateSeatsReservation(reservedSeats = bookings))
                        }
                    }
            }
        }

        fun changeBookingsByDate(date: LocalDate) {
            scope.launch(Dispatchers.IO) {
                bookingsUseCase
                    .getBookingsByDate(date = date, coroutineScope = this)
                    .collect { bookings ->
                        withContext(Dispatchers.Main) {
                            dispatch(
                                Msg.UpdateSeatReservationByDate(
                                    date = date,
                                    reservedSeats = bookings
                                )
                            )
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
                    reservedSeats = message.reservedSeats
                )
            }
    }
}


