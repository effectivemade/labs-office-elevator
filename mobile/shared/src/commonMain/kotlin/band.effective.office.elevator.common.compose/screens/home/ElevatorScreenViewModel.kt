package band.effective.office.elevator.common.compose.screens.home

import band.effective.office.elevator.common.compose.network.ktorClient
import band.effective.office.elevator.common.compose.screens.login.GoogleAuthorization
import com.adeo.kviewmodel.BaseSharedViewModel
import io.ktor.client.request.*
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
internal class ElevatorScreenViewModel :
    BaseSharedViewModel<ElevatorState, ElevatorAction, ElevatorEvent>(initialState = ElevatorState()) {

    private val mutableMessageStateFlow = MutableStateFlow("Press button to call elevator")
    val messageState = mutableMessageStateFlow.asStateFlow()

    private val mutableButtonStateFlow = MutableStateFlow(false)
    val buttonState = mutableButtonStateFlow.asStateFlow()

    init {
        viewModelScope.launch {
            mutableButtonStateFlow.debounce(1000).collectLatest { state ->
                delay(500)
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
                    delay(500)
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