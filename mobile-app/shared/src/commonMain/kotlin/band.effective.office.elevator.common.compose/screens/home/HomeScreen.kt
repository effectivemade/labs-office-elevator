package band.effective.office.elevator.common.compose.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import band.effective.office.elevator.common.compose.components.ElevatorButton
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import io.ktor.client.call.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
internal fun HomeScreen() {
    var isActive by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    var requestInfo by remember { mutableStateOf("") }

    Napier.base(DebugAntilog())

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            requestInfo, modifier = Modifier.padding(50.dp).align(Alignment.TopCenter)
        )
        ElevatorButton(
            modifier = Modifier.align(Alignment.Center), isActive = isActive, isEnabled = !isActive
        ) {
            if (!isActive) {
                scope.launch {
                    ElevatorController.callElevator().onSuccess { response ->
                        Napier.e(response.body(), null, null)
                        when (response.status.value) {
                            in 200..299 -> {
                                requestInfo = "Success"
                                delay(5000)
                                requestInfo = ""
                            }
                            404 -> {
                                requestInfo = "Not found"
                            }
                            403 -> {
                                requestInfo = "Access denied"
                            }
                            500 -> {
                                requestInfo = "Internal server error"
                            }
                        }
                        isActive = false
                    }.onFailure {
                        requestInfo = "Something went wrong"
                        Napier.e(throwable = it, tag = null) { "Error" }
                        isActive = false
                    }
                }
                isActive = !isActive
            }
        }
    }
}