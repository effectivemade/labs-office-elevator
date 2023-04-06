package band.effective.office.elevator.common.compose.screens.home

import band.effective.office.elevator.common.compose.expects.generateVibration
import band.effective.office.elevator.common.compose.network.ktorClient
import band.effective.office.elevator.common.compose.screens.login.GoogleAuthorization
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import io.ktor.client.request.*
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
internal class ElevatorScreenViewModel : ScreenModel {

    private val mutableMessageStateFlow =
        MutableStateFlow<ElevatorMessageState>(ElevatorMessageState.Start)
    val messageState = mutableMessageStateFlow.asStateFlow()

    private val mutableButtonStateFlow = MutableStateFlow(false)
    val buttonState = mutableButtonStateFlow.asStateFlow()

    init {
        load()
    }

    /***
     * This method must be deleted when `ScreenModel` will work properly
     */
    private fun load() {
        mutableMessageStateFlow.update { ElevatorMessageState.Start }
        coroutineScope.launch {
            mutableButtonStateFlow.onEach { isEnable -> if (isEnable) generateVibration(50) }
                .debounce(1000).collectLatest { state ->
                    delay(500)
                    if (state) {
                        GoogleAuthorization.performWithFreshToken(action = { token ->
                            coroutineScope.launch {
                                doNetworkElevatorCall(token)
                            }
                        }, failure = { message ->
                            mutableMessageStateFlow.update { ElevatorMessageState.AuthorizationError }
                            mutableButtonStateFlow.update { false }
                        })
                    }
                }
        }
    }

    fun sendEvent(viewEvent: ElevatorEvent) {
        when (viewEvent) {
            ElevatorEvent.CallElevator -> handleElevatorRequest()
            ElevatorEvent.SignOut -> GoogleAuthorization.signOut()
        }
    }

    private fun handleElevatorRequest() {
        mutableMessageStateFlow.update { ElevatorMessageState.Loading }
        mutableButtonStateFlow.update { true }
    }

    private suspend fun doNetworkElevatorCall(token: String) {
        try {
            val response = ktorClient.get("elevate") {
                parameter("key", token)
            }
            when (response.status.value) {
                in 200..299 -> {
                    mutableMessageStateFlow.update { ElevatorMessageState.Success }
                    delay(500)
                    mutableMessageStateFlow.update { ElevatorMessageState.Empty }
                    mutableButtonStateFlow.update { false }
                }
                403 -> {
                    mutableMessageStateFlow.update { ElevatorMessageState.IncorrectToken }
                    mutableButtonStateFlow.update { false }
                }
                else -> {
                    mutableMessageStateFlow.update { ElevatorMessageState.UndefinedError }
                    mutableButtonStateFlow.update { false }
                }
            }
        } catch (cause: Throwable) {
            mutableMessageStateFlow.update {
                ElevatorMessageState.UndefinedError
            }
            mutableButtonStateFlow.update { false }
        } finally {
            generateVibration(50)
        }
    }
}