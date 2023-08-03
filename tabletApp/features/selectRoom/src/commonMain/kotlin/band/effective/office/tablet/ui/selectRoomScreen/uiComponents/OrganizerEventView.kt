package band.effective.office.tablet.ui.selectRoomScreen.uiComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
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
import band.effective.office.tablet.ui.theme.CustomDarkColors
import band.effective.office.tablet.ui.theme.LocalCustomColorsPalette

@Composable
fun OrganizerEventView(modifier: Modifier, booking: Booking) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = booking.eventInfo.organizer,
            style = MaterialTheme.typography.h6,
            color = LocalCustomColorsPalette.current.primaryTextAndIcon,
            textAlign = TextAlign.Center
        )
    }
}