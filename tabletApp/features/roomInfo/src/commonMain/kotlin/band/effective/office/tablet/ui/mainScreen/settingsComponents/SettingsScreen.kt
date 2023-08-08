package band.effective.office.tablet.ui.mainScreen.settingsComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import band.effective.office.tablet.ui.mainScreen.settingsComponents.uiComponents.ExitButtonView
import band.effective.office.tablet.ui.mainScreen.settingsComponents.uiComponents.TitleView

@Composable
fun SettingsScreen(component: SettingsComponent){
    val state by component.state.collectAsState()

}

@Composable
fun SettingsView(){
    val padding = 35.dp
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        ExitButtonView(
            modifier = Modifier
                .fillMaxHeight(0.2f)
                .padding(start = padding)
        )
        TitleView(modifier = Modifier.padding(start = padding))
    }
}