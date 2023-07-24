package band.effective.office.tablet.ui.mainScreen.bookingRoomComponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
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
import band.effective.office.tablet.ui.mainScreen.bookingRoomComponents.uiComponents.Alert
import band.effective.office.tablet.ui.mainScreen.bookingRoomComponents.uiComponents.BusyAlertView
import band.effective.office.tablet.ui.mainScreen.bookingRoomComponents.uiComponents.DateTimeView
import band.effective.office.tablet.ui.mainScreen.bookingRoomComponents.uiComponents.EventLengthView
import band.effective.office.tablet.ui.mainScreen.bookingRoomComponents.uiComponents.EventOrganizerView

@Composable
fun BookingRoomView(modifier: Modifier = Modifier, bookingRoomComponent: BookingRoomComponent) {
    val state by bookingRoomComponent.state.collectAsState()
    Surface(modifier = modifier.fillMaxSize(), color = MaterialTheme.colors.surface) {
        Column(modifier = Modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.height(63.dp))
            Text(
                text = MainRes.string.booking_view_title,
                style = MaterialTheme.typography.h3
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
            if (!state.isCorrectDate() || !state.isCorrectLength()) {
                Spacer(Modifier.height(10.dp))
                Alert(modifier = Modifier.fillMaxWidth(), text = MainRes.string.no_correct_time)
            }
            Spacer(modifier = Modifier.height(25.dp))
            EventOrganizerView(
                modifier = Modifier.fillMaxWidth().height(100.dp),
                component = bookingRoomComponent.eventOrganizerComponent,
                organizers = state.organizers
            )
            if (!state.isCorrectOrganizer()) {
                Spacer(Modifier.height(10.dp))
                Alert(
                    modifier = Modifier.fillMaxWidth(),
                    text = MainRes.string.no_select_organizer_alert
                )
            }
            Spacer(modifier = Modifier.height(50.dp))
        }
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            Button(
                modifier = Modifier.fillMaxWidth().height(60.dp).clip(RoundedCornerShape(100.dp)),
                onClick = { bookingRoomComponent.bookingCurrentRoom() },
                enabled = !state.isBusy
            ) {
                Text(text = MainRes.string.booking_button_text.format(roomName = state.roomName))
            }
            Spacer(Modifier.height(10.dp))
            Button(
                modifier = Modifier.fillMaxWidth().height(60.dp).clip(RoundedCornerShape(100.dp)),
                onClick = { bookingRoomComponent.bookingCurrentRoom() },
                enabled = !state.isBusy
            ) {
                Text(text = MainRes.string.booking_button_text.format(roomName = state.roomName))
            }
        }
    }
}