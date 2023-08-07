package band.effective.office.tablet.ui.mainScreen.settingsComponents

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun SettingsScreen(component: SettingsComponent){
    val state by component.state.collectAsState()
}