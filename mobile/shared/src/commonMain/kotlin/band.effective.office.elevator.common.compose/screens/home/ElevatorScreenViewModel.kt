package band.effective.office.elevator.common.compose.screens.home

import band.effective.office.elevator.common.compose.network.ktorClient
import band.effective.office.elevator.common.compose.screens.login.GoogleAuthorization
import io.ktor.client.request.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

@OptIn(FlowPreview::class)
object ElevatorScreenViewModel {

    sealed class Event {
        object SignOut : Event()
        object CallElevator : Event()
    }

    private val mutableMessageStateFlow = MutableStateFlow("Press button to call elevator")
    val messageState = mutableMessageStateFlow.asStateFlow()

    private val mutableButtonStateFlow = MutableStateFlow(false)
    val buttonState = mutableButtonStateFlow.asStateFlow()

    private val scope = CoroutineScope(Dispatchers.Main)

    init {
        scope.launch {
            mutableButtonStateFlow.collectLatest { state ->
                if (state){
                    GoogleAuthorization.performWithFreshToken(
                        action = { token ->
                            scope.launch {
                                doNetworkElevatorCall(token)
                            }
                        },
                        failure = { message ->
                            mutableMessageStateFlow.update { message }
                            mutableButtonStateFlow.update { false }
                        }
                    )
                }
            }
        }
    }

    fun sendEvent(event: Event) = scope.launch {
        when (event) {
            Event.CallElevator -> handleElevatorRequest()
            Event.SignOut -> GoogleAuthorization.signOut()
        }
    }

    private suspend fun handleElevatorRequest() {
        mutableMessageStateFlow.update { "Attempt to call elevator" }
        mutableButtonStateFlow.update { true }
    }

    private suspend fun doNetworkElevatorCall(token: String) {
        try {
            val response = ktorClient.get("elevate") {
                parameter("key", token)
            }
            when (response.status.value) {
                in 200..299 -> {
                    mutableMessageStateFlow.update { "Success" }
                    delay(1500)
                    mutableMessageStateFlow.update { "" }
                    mutableButtonStateFlow.update { false }
                }
                403 -> {
                    mutableMessageStateFlow.update { "Could not verify you Google account. Please try again or re-authenticate in app" }
                    mutableButtonStateFlow.update { false }
                }
                else -> {
                    mutableMessageStateFlow.update { "Oops, something went wrong. Please try again" }
                    mutableButtonStateFlow.update { false }
                }
            }
        } catch (cause: Throwable) {
            mutableMessageStateFlow.update {
                cause.message?.substringBefore("[") ?: "Something went wrong"
            }
            mutableButtonStateFlow.update { false }
        }
    }
}