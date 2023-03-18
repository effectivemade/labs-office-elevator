package band.effective.office.elevator.common.compose.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import band.effective.office.elevator.common.compose.components.ElevatorButton

@Composable
internal fun ElevatorScreen(
    onSignOut: () -> Unit
) {
    val message by ElevatorScreenViewModel.messageState.collectAsState()
    val buttonIsActive by ElevatorScreenViewModel.buttonState.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(30.dp))
        Text(message, modifier = Modifier.padding(50.dp).align(Alignment.TopCenter), textAlign = TextAlign.Center)
        ElevatorButton(
            modifier = Modifier.align(Alignment.Center),
            isActive = buttonIsActive,
            isEnabled = !buttonIsActive
        ) {
            ElevatorScreenViewModel.sendEvent(ElevatorScreenViewModel.Event.CallElevator)
        }
        IconButton(
            onClick = {
                ElevatorScreenViewModel.sendEvent(ElevatorScreenViewModel.Event.SignOut)
                onSignOut()
            }, modifier = Modifier.align(Alignment.TopEnd)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.ExitToApp, contentDescription = null)
                Text("SIGN OUT", style = typography.overline)
            }
        }
    }
}