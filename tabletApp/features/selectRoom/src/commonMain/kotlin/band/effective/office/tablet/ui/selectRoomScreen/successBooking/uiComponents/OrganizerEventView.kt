package band.effective.office.tablet.ui.selectRoomScreen.successBooking.uiComponents

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import band.effective.office.tablet.domain.model.Booking
import band.effective.office.tablet.ui.theme.LocalCustomColorsPalette

@Composable
fun OrganizerEventView(booking: Booking) {
    Text(
        text = booking.eventInfo.organizer,
        style = MaterialTheme.typography.h5,
        fontFamily = FontFamily.SansSerif,
        color = LocalCustomColorsPalette.current.primaryTextAndIcon,
        fontWeight = FontWeight(500)
        //textAlign = TextAlign.Center
    )
}