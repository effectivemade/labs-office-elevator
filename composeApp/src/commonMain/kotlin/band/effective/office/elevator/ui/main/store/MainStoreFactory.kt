package band.effective.office.elevator.ui.main.store

import band.effective.office.elevator.MainRes
import band.effective.office.elevator.data.ApiResponse
import band.effective.office.elevator.domain.OfficeElevatorRepository
import band.effective.office.elevator.ui.main.store.MainStore.Intent
import band.effective.office.elevator.ui.main.store.MainStore.State
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
        object : MainStore, Store<Intent, State, MainStore.Label> by storeFactory.create(
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
        CoroutineExecutor<Intent, Nothing, State, Msg, MainStore.Label>() {
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
                dispatch(Msg.SwitchButton(false))
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
