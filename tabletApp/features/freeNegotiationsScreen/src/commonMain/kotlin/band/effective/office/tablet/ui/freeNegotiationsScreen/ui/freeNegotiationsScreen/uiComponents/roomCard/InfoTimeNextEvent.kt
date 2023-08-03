package band.effective.office.tablet.ui.freeNegotiationsScreen.ui.freeNegotiationsScreen.uiComponents.roomCard

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import band.effective.office.tablet.ui.theme.LocalCustomColorsPalette

@Composable
fun InfoTimeNextEvent(info: String){
    Text(
        text = info,
        style = MaterialTheme.typography.h6,
        color = LocalCustomColorsPalette.current.secondaryTextAndIcon
    )
}