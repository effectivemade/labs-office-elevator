package band.effective.office.tablet.ui.mainScreen.components.mockComponets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color

@Composable
fun MockSettingView(component: MockSettingsComponent) {
    val state by component.state.collectAsState()

    Column {
        Item(
            checked = state.isBusy,
            onCheckedChange = { component.sendEvent(MockSettingsEvent.OnSwitchBusy(it)) },
            text = "Комната занята"
        )
        Item(
            checked = state.isManyEvent,
            onCheckedChange = { component.sendEvent(MockSettingsEvent.OnSwitchEventCount(it)) },
            text = "Много меропритий"
        )
        Item(
            checked = state.isHaveTv,
            onCheckedChange = { component.sendEvent(MockSettingsEvent.OnSwitchTv(it)) },
            text = "Есть tv"
        )
    }
}

@Composable
private fun Item(checked: Boolean, onCheckedChange: (Boolean) -> Unit, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = { onCheckedChange(it) },
            colors = CheckboxDefaults.colors(uncheckedColor = Color.White),
        )
        Text(text = text, color = Color.White)
    }
}