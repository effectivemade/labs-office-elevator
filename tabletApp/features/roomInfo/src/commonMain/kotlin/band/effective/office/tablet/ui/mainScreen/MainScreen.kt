package band.effective.office.tablet.ui.mainScreen

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import band.effective.office.tablet.ui.mainScreen.MainComponent

@Composable
fun MainScreen(component: MainComponent) {
    val state by component.state.collectAsState()
    MainScreenView(
        buttonText = state.platform,
        onClick = { component.sendEvent(MainScreenEvent.OnCLick) }
    )
}

@Composable
fun MainScreenView(buttonText: String, onClick: () -> Unit) {
    Button(onClick = { onClick() }) {
        Text(text = "Hello, $buttonText")
    }
}