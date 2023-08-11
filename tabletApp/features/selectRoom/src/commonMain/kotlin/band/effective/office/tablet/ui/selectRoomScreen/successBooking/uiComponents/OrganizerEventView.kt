package band.effective.office.tablet.ui.selectRoomScreen.successBooking.uiComponents

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import band.effective.office.tablet.domain.model.Booking
import band.effective.office.tablet.ui.theme.LocalCustomColorsPalette

@Composable
fun OrganizerEventView(booking: Booking) {
    Text(
        text = booking.eventInfo.organizer.fullName,
        style = MaterialTheme.typography.h5,
        color = LocalCustomColorsPalette.current.primaryTextAndIcon
    )
}