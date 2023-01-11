package band.effective.office.elevator.common.compose.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import band.effective.office.elevator.common.compose.components.ElevatorButton
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
internal fun HomeScreen(
    onSignOut: () -> Unit
) {
    val message by HomeScreenViewModel.messageState.collectAsState()
    val buttonIsActive by HomeScreenViewModel.buttonState.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        Text(message, modifier = Modifier.padding(50.dp).align(Alignment.TopCenter))
        ElevatorButton(
            modifier = Modifier.align(Alignment.Center),
            isActive = buttonIsActive,
            isEnabled = !buttonIsActive
        ) {
            HomeScreenViewModel.sendEvent(HomeScreenViewModel.Event.CallElevator)
        }
        IconButton(
            onClick = {
                HomeScreenViewModel.sendEvent(HomeScreenViewModel.Event.SignOut)
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