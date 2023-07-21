package band.effective.office.tablet.ui.selectRoomScreen.successBooking

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import band.effective.office.tablet.domain.model.Booking
import band.effective.office.tablet.features.selectRoom.MainRes
import band.effective.office.tablet.ui.selectRoomScreen.successBooking.uiComponents.IconSuccess
import band.effective.office.tablet.ui.selectRoomScreen.successBooking.uiComponents.SuccessText
import band.effective.office.tablet.ui.selectRoomScreen.uiComponents.BookingButtonView
import band.effective.office.tablet.ui.selectRoomScreen.uiComponents.CrossButtonView
import band.effective.office.tablet.ui.selectRoomScreen.successBooking.uiComponents.DateTimeView
import band.effective.office.tablet.ui.selectRoomScreen.successBooking.uiComponents.OrganizerEventView
import band.effective.office.tablet.ui.theme.LocalCustomColorsPalette

@Composable
fun SuccessSelectRoomView(
    booking: Booking,
    close: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(551.dp, 530.dp)
            .clip(RoundedCornerShape(3))
            .background(LocalCustomColorsPalette.current.elevationBackground),
    ) {
        Column(
            modifier = Modifier
                .matchParentSize()
                .padding(top = 35.dp, bottom = 80.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CrossButtonView(
                Modifier.width(551.dp),
                onDismissRequest = { close() }
            )
            Spacer(modifier = Modifier.height(2.dp))
            IconSuccess()
            Spacer(modifier = Modifier.height(24.dp))
            SuccessText(modifier = Modifier.width(227.dp), nameRoom = booking.nameRoom)
            Spacer(modifier = Modifier.height(50.dp))
            DateTimeView(modifier = Modifier.width(391.dp), booking = booking)
            Spacer(modifier = Modifier.height(12.dp))
            OrganizerEventView(booking = booking)
            Spacer(modifier = Modifier.height(60.dp))
            BookingButtonView(
                modifier = Modifier.height(70.dp).width(395.dp),
                shape = RoundedCornerShape(100),
                text = MainRes.string.on_main,
                onClick = {
                    close()
                }
            )
        }
    }
}