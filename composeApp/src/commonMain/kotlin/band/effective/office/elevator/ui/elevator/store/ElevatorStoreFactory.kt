package band.effective.office.elevator.ui.elevator.store

import band.effective.office.elevator.data.ApiResponse
import band.effective.office.elevator.MR
import band.effective.office.elevator.domain.OfficeElevatorRepository
import band.effective.office.elevator.ui.elevator.store.ElevatorStore.Intent
import band.effective.office.elevator.ui.elevator.store.ElevatorStore.State
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import dev.icerock.moko.resources.compose.stringResource
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal class ElevatorStoreFactory(
    private val storeFactory: StoreFactory
) : KoinComponent {

    private val officeElevatorRepository: OfficeElevatorRepository by inject()

    fun create(): ElevatorStore =
        object : ElevatorStore, Store<Intent, State, ElevatorStore.Label> by storeFactory.create(
            name = "ElevatorStore",
            initialState = State(
                buttonActive = false,
            ),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed interface Msg {
        data class SwitchButton(val isActive: Boolean) : Msg
    }

    private inner class ExecutorImpl :
        CoroutineExecutor<Intent, Nothing, State, Msg, ElevatorStore.Label>() {
        override fun executeIntent(intent: Intent, getState: () -> State) {
            when (intent) {
                Intent.OnButtonClicked -> {
                    if (!getState().buttonActive)
                        doElevatorCall()
                }
            }
        }

        private fun doElevatorCall() {
            scope.launch {
                dispatch(Msg.SwitchButton(true))
                delay(1000)
                /*
                publish(
                    when (val result = officeElevatorRepository.call()) {
                        is ApiResponse.Error.HttpError -> ElevatorStore.Label.ShowError(
                            ElevatorStore.ErrorState(stringResource(MR.strings.server_error).format(result.code.toString())
                            )
                        )

                        ApiResponse.Error.NetworkError -> ElevatorStore.Label.ShowError(
                            ElevatorStore.ErrorState(
                                stringResource(MR.strings.network_error)
                            )
                        )

                        ApiResponse.Error.SerializationError -> ElevatorStore.Label.ShowError(
                            ElevatorStore.ErrorState(
                                stringResource(MR.strings.developer_error)
                            )
                        )

                        ApiResponse.Error.UnknownError -> ElevatorStore.Label.ShowError(
                            ElevatorStore.ErrorState(
                                stringResource(MR.strings.developer_error)
                            )
                        )

                        is ApiResponse.Success -> {
                            ElevatorStore.Label.ShowSuccess
                        }
                    }
                )
                delay(1000)
                dispatch(Msg.SwitchButton(false))
                 */
            }
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(message: Msg): State =
            when (message) {
                is Msg.SwitchButton -> copy(buttonActive = message.isActive)
            }
    }
}
