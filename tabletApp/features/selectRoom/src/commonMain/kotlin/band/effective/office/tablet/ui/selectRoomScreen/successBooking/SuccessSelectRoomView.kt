package band.effective.office.tablet.ui.selectRoomScreen.successBooking

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import band.effective.office.tablet.domain.model.Booking
import band.effective.office.tablet.features.selectRoom.MainRes
import band.effective.office.tablet.ui.selectRoomScreen.successBooking.uiComponents.DateTimeView
import band.effective.office.tablet.ui.selectRoomScreen.successBooking.uiComponents.IconSuccess
import band.effective.office.tablet.ui.selectRoomScreen.successBooking.uiComponents.OrganizerEventView
import band.effective.office.tablet.ui.selectRoomScreen.successBooking.uiComponents.SuccessText
import band.effective.office.tablet.ui.selectRoomScreen.uiComponents.BookingButtonView
import band.effective.office.tablet.ui.selectRoomScreen.uiComponents.CrossButtonView
import band.effective.office.tablet.ui.theme.LocalCustomColorsPalette

@Composable
fun SuccessSelectRoomView(
    booking: Booking,
    close: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(0.47f)
            .fillMaxHeight(0.67f)
            .clip(RoundedCornerShape(3))
            .background(LocalCustomColorsPalette.current.elevationBackground)
            .padding(top = 35.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CrossButtonView(
            Modifier
                .fillMaxWidth()
                .padding(end = 42.dp),
            onDismissRequest = { close() }
        )
        Spacer(modifier = Modifier.height(2.dp))
        IconSuccess()
        Spacer(modifier = Modifier.height(24.dp))
        SuccessText(
            modifier = Modifier.fillMaxWidth(),
            nameRoom = booking.nameRoom
        )
        Spacer(modifier = Modifier.height(50.dp))
        DateTimeView(
            modifier = Modifier.fillMaxWidth(),
            booking = booking
        )
        Spacer(modifier = Modifier.height(12.dp))
        OrganizerEventView(booking = booking)
        Spacer(modifier = Modifier.height(30.dp))
        BookingButtonView(
            modifier = Modifier
                .fillMaxWidth(0.72f)
                .height(64.dp),
            shape = RoundedCornerShape(100),
            text = MainRes.string.on_main,
            onClick = {
                close()
            },
            isLoading = false
        )
    }
}