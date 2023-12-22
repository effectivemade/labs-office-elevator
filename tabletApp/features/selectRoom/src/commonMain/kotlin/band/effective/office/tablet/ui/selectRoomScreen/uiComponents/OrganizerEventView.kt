package band.effective.office.tablet.ui.selectRoomScreen.uiComponents

import androidx.compose.foundation.layout.Box
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import band.effective.office.tablet.domain.model.Booking
import band.effective.office.tablet.ui.theme.LocalCustomColorsPalette

@Composable
fun OrganizerEventView(modifier: Modifier, booking: Booking) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = booking.eventInfo.organizer.fullName,
            style = MaterialTheme.typography.h6,
            color = LocalCustomColorsPalette.current.primaryTextAndIcon,
            textAlign = TextAlign.Center
        )
    }
}