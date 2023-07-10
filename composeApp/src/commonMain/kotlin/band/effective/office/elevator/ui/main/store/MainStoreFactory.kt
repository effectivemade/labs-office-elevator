package band.effective.office.elevator.ui.main.store

import band.effective.office.elevator.MainRes
import band.effective.office.elevator.data.ApiResponse
import band.effective.office.elevator.domain.OfficeElevatorRepository
import band.effective.office.elevator.ui.models.ElevatorState
import band.effective.office.elevator.ui.models.ReservedSeat
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.format
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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
                reservedSeats = listOf()
            ),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed interface Msg {
        data class UpdateElevatorState(val elevatorState: ElevatorState) : Msg
        data class UpdateSeatsReservation(val reservedSeats: List<ReservedSeat>) : Msg

    }

    private inner class ExecutorImpl :
        CoroutineExecutor<MainStore.Intent, Nothing, MainStore.State, Msg, MainStore.Label>() {
        override fun executeIntent(intent: MainStore.Intent, getState: () -> MainStore.State) {
            when (intent) {
                MainStore.Intent.OnClickCallElevator -> {
                    if (getState().elevatorState is ElevatorState.Below)
                        doElevatorCall()
                }
                MainStore.Intent.OnClickShowOption -> TODO()
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
            }
    }
}
