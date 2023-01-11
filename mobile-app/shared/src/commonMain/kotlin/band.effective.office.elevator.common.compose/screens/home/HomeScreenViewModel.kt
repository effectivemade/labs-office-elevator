package band.effective.office.elevator.common.compose.screens.home

import band.effective.office.elevator.common.compose.network.ktorClient
import band.effective.office.elevator.common.compose.screens.login.GoogleAuthorization
import io.ktor.client.request.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

object HomeScreenViewModel {

    sealed class Event {
        object SignOut : Event()
        object CallElevator : Event()
    }

    private val mutableMessageStateFlow = MutableStateFlow("Press button to call elevator")
    val messageState = mutableMessageStateFlow.asStateFlow()

    private val mutableButtonStateFlow = MutableStateFlow(false)
    val buttonState = mutableButtonStateFlow.asStateFlow()

    private val scope = CoroutineScope(Dispatchers.Main)

    fun sendEvent(event: Event) = scope.launch {
        when (event) {
            Event.CallElevator -> handleElevatorRequest()
            Event.SignOut -> GoogleAuthorization.signOut()
        }
    }

    private suspend fun handleElevatorRequest() {
        mutableMessageStateFlow.update { "Attempt to call elevator" }
        mutableButtonStateFlow.update { true }
        delay(1000)
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

    private suspend fun doNetworkElevatorCall(token: String) {
        val response = ktorClient.get("elevate") {
            parameter("key", token)
        }
        when (response.status.value) {
            in 200..299 -> {
                mutableMessageStateFlow.update { "Success" }
                delay(2000)
                mutableMessageStateFlow.update { "" }
                mutableButtonStateFlow.update { false }
            }
            else -> {
                mutableMessageStateFlow.update { response.status.description }
                mutableButtonStateFlow.update { false }
            }
        }

    }
}