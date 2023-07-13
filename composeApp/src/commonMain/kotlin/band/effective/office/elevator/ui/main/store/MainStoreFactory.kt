package band.effective.office.elevator.ui.main.store

import band.effective.office.elevator.MainRes
import band.effective.office.elevator.data.ApiResponse
import band.effective.office.elevator.domain.OfficeElevatorRepository
import band.effective.office.elevator.ui.models.ElevatorState
import band.effective.office.elevator.ui.models.ReservedSeat
import band.effective.office.elevator.utils.getCurrentDate
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.format
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal class MainStoreFactory(
    private val storeFactory: StoreFactory
) : KoinComponent {

    private val officeElevatorRepository: OfficeElevatorRepository by inject()

    fun create(): MainStore =
        object : MainStore, Store<MainStore.Intent, MainStore.State, MainStore.Label> by storeFactory.create(
            name = "MainStore",
            initialState = MainStore.State(
                elevatorState = ElevatorState.Below,
                reservedSeats = listOf(),
                currentDate = getCurrentDate()
            ),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed interface Msg {
        data class UpdateElevatorState(val elevatorState: ElevatorState) : Msg
        data class UpdateSeatsReservation(val reservedSeats: List<ReservedSeat>) : Msg

        data class UpdateCurrentDate(val date: LocalDate?) : Msg
    }

    private inner class ExecutorImpl :
        CoroutineExecutor<MainStore.Intent, Nothing, MainStore.State, Msg, MainStore.Label>() {
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
                    scope.launch {
                        dispatch(Msg.UpdateCurrentDate(intent.date))
                        publish(MainStore.Label.CloseCalendar)
                    }
                }
            }
        }

        private fun doElevatorCall() {
            scope.launch {
                dispatch(Msg.UpdateElevatorState(ElevatorState.Goes))
                delay(1000)
                publish(
                    when (val result = officeElevatorRepository.call()) {
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
    }
    private object ReducerImpl : Reducer<MainStore.State, Msg> {
        override fun MainStore.State.reduce(message: Msg): MainStore.State =
            when (message) {
                is Msg.UpdateElevatorState -> copy(elevatorState = message.elevatorState)
                is Msg.UpdateSeatsReservation -> copy(reservedSeats = message.reservedSeats)
                is Msg.UpdateCurrentDate -> {
                    if (message.date == null) this
                    else {
                        val reservedSeats = mokValue.filter { it.bookingDate == message.date }
                        copy(currentDate = message.date, reservedSeats = reservedSeats)
                    }
                }
            }
    }
}

private val mokValue = listOf(
    ReservedSeat(
        seatName = "Рабочее масто А2",
        bookingDay = "Пн, 1 июля",
        bookingTime = "12:00 - 14:00",
        bookingDate = LocalDate(month = Month.JULY, year = 2023, dayOfMonth = 16)
    ),
    ReservedSeat(
        seatName = "Рабочее масто А1",
        bookingDay = "Пн, 1 июля",
        bookingTime = "12:00 - 14:00",
        bookingDate = LocalDate(month = Month.JULY, year = 2023, dayOfMonth = 17)
    ),
    ReservedSeat(
        seatName = "Рабочее масто А3",
        bookingDay = "Пн, 1 июля",
        bookingTime = "12:00 - 14:00",
        bookingDate = LocalDate(month = Month.JULY, year = 2023, dayOfMonth = 18)
    ),
)