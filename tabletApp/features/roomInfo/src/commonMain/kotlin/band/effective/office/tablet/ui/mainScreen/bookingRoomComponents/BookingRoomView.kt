package band.effective.office.tablet.ui.mainScreen.bookingRoomComponents

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import band.effective.office.tablet.features.roomInfo.MainRes
import band.effective.office.tablet.ui.mainScreen.bookingRoomComponents.uiComponents.BusyAlertView
import band.effective.office.tablet.ui.mainScreen.bookingRoomComponents.uiComponents.DateTimeView
import band.effective.office.tablet.ui.mainScreen.bookingRoomComponents.uiComponents.EventLengthView
import band.effective.office.tablet.ui.mainScreen.bookingRoomComponents.uiComponents.EventOrganizerView

@Composable
fun BookingRoomView(modifier: Modifier = Modifier, bookingRoomComponent: BookingRoomComponent) {
    val state by bookingRoomComponent.state.collectAsState()
    Surface(modifier = modifier.fillMaxSize(), color = Color(0xFF252322)) {
        Column(modifier = Modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.height(63.dp))
            Text(
                text = MainRes.string.booking_view_title,
                color = Color(0xFFFAFAFA),
                fontSize = 36.sp
            )
            Spacer(modifier = Modifier.height(25.dp))
            DateTimeView(
                modifier = Modifier.fillMaxWidth().height(100.dp),
                component = bookingRoomComponent.dateTimeComponent,
                selectDate = state.selectDate
            )
            Spacer(modifier = Modifier.height(25.dp))
            EventLengthView(
                modifier = Modifier.fillMaxWidth().height(100.dp),
                component = bookingRoomComponent.eventLengthComponent,
                currentLength = state.length,
                isBusy = state.isBusy
            )
            Spacer(modifier = Modifier.height(25.dp))
            EventOrganizerView(
                modifier = Modifier.fillMaxWidth().height(100.dp),
                component = bookingRoomComponent.eventOrganizerComponent,
                organizers = state.organizers
            )
            Spacer(modifier = Modifier.height(50.dp))
            if (state.isBusy) {
                BusyAlertView(
                    modifier = Modifier.fillMaxWidth(),
                    event = state.busyEvent,
                    onClick = { bookingRoomComponent.bookingOtherRoom() })
            }
        }
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
            Button(
                modifier = Modifier.fillMaxWidth().height(60.dp).clip(RoundedCornerShape(100.dp)),
                onClick = { bookingRoomComponent.bookingCurrentRoom() },
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color(0xFFFFFFFF),
                    backgroundColor = Color(0xFFEF7234),
                    disabledBackgroundColor = Color(0xFF342C28),
                    disabledContentColor = Color(0xFF808080)
                ),
                enabled = !state.isBusy
            ) {
                Text(text = MainRes.string.booking_button_text.format(roomName = state.roomName))
            }
        }
    }
}