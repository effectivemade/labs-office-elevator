package band.effective.office.tablet.ui.freeNegotiationsScreen.ui.freeNegotiationsScreen.uiComponents.roomCard

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import band.effective.office.tablet.ui.theme.LocalCustomColorsPalette

@Composable
fun NameRoom(
    nameRoom: String
){
    Text(
        text = nameRoom,
        style = MaterialTheme.typography.h3,
        color = LocalCustomColorsPalette.current.primaryTextAndIcon
    )
}