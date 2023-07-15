package band.effective.office.tablet.ui.selectRoomScreen.uiComponents

import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import band.effective.office.tablet.features.selectRoom.MainRes
import band.effective.office.tablet.ui.selectRoomScreen.SelectRoomComponentImpl
import band.effective.office.tablet.ui.theme.CustomDarkColors
import band.effective.office.tablet.ui.theme.LocalCustomColorsPalette

@Composable
fun Title(component: SelectRoomComponentImpl){
    Text(
        modifier = Modifier.width(415.dp),
        text = MainRes.string.title_booking_dialog.format(
            nameRoom = component.booking.nameRoom
        ),
        style = MaterialTheme.typography.h4,
        color = LocalCustomColorsPalette.current.primaryTextAndIcon,
        fontFamily = FontFamily.SansSerif,
        textAlign = TextAlign.Center
    )
}