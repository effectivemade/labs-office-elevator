package band.effective.office.tablet.ui.selectRoomScreen.uiComponents

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import band.effective.office.tablet.domain.model.Booking

@Composable
fun OrganizerEventView(modifier: Modifier, shape: RoundedCornerShape, booking: Booking) {
    Card(
        shape = shape,
        backgroundColor = Color(0xFF3A3736)) {
        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center) {
            Text(
                text = booking.eventInfo.organizer,
                fontWeight = FontWeight(700),
                fontSize = 20.sp,
                fontFamily = FontFamily.SansSerif,
                color = Color(0xFFFAFAFA),
                textAlign = TextAlign.Center
            )
        }
    }
}