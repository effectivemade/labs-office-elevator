package band.effective.office.tablet.ui.selectRoomScreen.uiComponents

import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import band.effective.office.tablet.features.selectRoom.MainRes
import band.effective.office.tablet.ui.selectRoomScreen.RealSelectRoomComponent

@Composable
fun Title(component: RealSelectRoomComponent){
    Text(
        modifier = Modifier.width(415.dp),
        text = MainRes.string.title_booking_dialog.format(
            nameRoom = component.booking.nameRoom
        ),
        fontSize = 28.sp,
        fontWeight = FontWeight(500),
        fontFamily = FontFamily.SansSerif,
        color = Color(0xFFFAFAFA),
        textAlign = TextAlign.Center
    )
}