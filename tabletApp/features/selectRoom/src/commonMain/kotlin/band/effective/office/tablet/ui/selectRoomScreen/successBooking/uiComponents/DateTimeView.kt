package band.effective.office.tablet.ui.selectRoomScreen.successBooking.uiComponents

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import band.effective.office.tablet.domain.model.Booking
import band.effective.office.tablet.features.selectRoom.MainRes
import band.effective.office.tablet.ui.theme.LocalCustomColorsPalette
import band.effective.office.tablet.utils.date
import band.effective.office.tablet.utils.time24

@Composable
fun DateTimeView(modifier: Modifier, booking: Booking) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = MainRes.string.date_booking.format(
                startTimeDate = booking.eventInfo.startTime.date(),
                startTime = booking.eventInfo.startTime.time24(),
                finishTime = booking.eventInfo.finishTime.time24()
            ),
            style = MaterialTheme.typography.h5,
            color = LocalCustomColorsPalette.current.primaryTextAndIcon
        )
    }
}