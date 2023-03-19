package band.effective.office.elevator.common.compose.screens.home

import band.effective.office.elevator.common.compose.network.ktorClient
import band.effective.office.elevator.common.compose.screens.login.GoogleAuthorization
import com.adeo.kviewmodel.BaseSharedViewModel
import io.github.aakira.napier.Napier
import io.ktor.client.request.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class ElevatorScreenViewModel :
    BaseSharedViewModel<ElevatorState, ElevatorAction, ElevatorEvent>(initialState = ElevatorState()) {

    private val mutableMessageStateFlow = MutableStateFlow("Press button to call elevator")
    val messageState = mutableMessageStateFlow.asStateFlow()

    private val mutableButtonStateFlow = MutableStateFlow(false)
    val buttonState = mutableButtonStateFlow.asStateFlow()

    init {
        viewModelScope.launch {
            Napier.d { "ElevatorScreen create viewmodel" }
            mutableButtonStateFlow.collectLatest { state ->
                if (state) {
                    GoogleAuthorization.performWithFreshToken(
                        action = { token ->
                            viewModelScope.launch {
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

    override fun obtainEvent(viewEvent: ElevatorEvent) {
        when (viewEvent) {
            ElevatorEvent.CallElevator -> handleElevatorRequest()
            ElevatorEvent.SignOut -> GoogleAuthorization.signOut()
        }
    }

    private fun handleElevatorRequest() {
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